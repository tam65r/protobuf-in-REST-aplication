package com.example.sisdi_users.usermanagement.repositories;

import com.example.sisdi_users.auth.api.AuthRequest;
import com.example.sisdi_users.exceptions.DuplicatedDataException;
import com.example.sisdi_users.exceptions.InconsistencyDataException;
import com.example.sisdi_users.exceptions.NotFoundException;
import com.example.sisdi_users.usermanagement.api.CreateSubscriberRequest;
import com.example.sisdi_users.usermanagement.model.User;

import com.example.sisdi_users.usermanagement.service.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

import static java.lang.String.format;
import static java.util.stream.Collectors.joining;

@RequiredArgsConstructor
@Component
public class UserRepositoryImpl implements UserRepository {
    private final UserDBRepository dbRepository;
    private final UserHTTPRepository httpRepository;
    private final AuthenticationManager authenticationManager;
    private final SubscriptionRepository subscriptionRepository;
    private final JwtEncoder jwtEncoder;

    @Override
    public User create(User user, CreateSubscriberRequest request) throws Exception {
        final var userOpt = dbRepository.findByUsername(user.getUsername());
        if(userOpt.isEmpty()){ //nao existe "neste"
            if(httpRepository.getByUsername(user.getUsername()) == null){ //nao existe "no outro"
                if (request != null) {
                    if (subscriptionRepository.postSubscription(request) == HttpStatus.SC_CREATED) {
                        return dbRepository.save(user);
                    }
                }
                return dbRepository.save(user);
            }
        }
        throw new DuplicatedDataException(User.class,user.getUsername());
    }

    @Override
    public User findByUsername (String username, boolean internal) throws Exception{
        Optional<User> userBD = dbRepository.findByUsername(username);

        if (userBD.isPresent()) {
            return userBD.get();
        }

        if(!internal) {
            User userHttp = httpRepository.getByUsername(username);
            if (userHttp != null) {
                return userHttp;
            }
        }


        throw new NotFoundException(User.class,username);
    }

    @Override
    public String login(AuthRequest request, boolean internal) throws Exception {

        Optional<User> userDB = dbRepository.findByUsername(request.getUsername());
        if (userDB.isEmpty()) {
            if (httpRepository.getByUsername(request.getUsername()) == null) {
                throw new NotFoundException(request.getUsername());
            } else {
                return httpRepository.login(request);
            }
        }

        try {
            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            final User user = (User) authentication.getPrincipal();

            final Instant now = Instant.now();
            final long expiry = 36000L;

            final String scope = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                    .collect(joining(" "));

            final JwtClaimsSet claims = JwtClaimsSet.builder().issuer("example.io").issuedAt(now)
                    .expiresAt(now.plusSeconds(expiry)).subject(format("%s,%s", user.getId(), user.getUsername()))
                    .claim("roles", scope).build();

            final String token = this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

            return token;
        } catch (final BadCredentialsException ex) {
            throw new InconsistencyDataException(request.getUsername());
        }
    }



}
