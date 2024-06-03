package com.example.sisdi_users.usermanagement.repositories;

import com.example.sisdi_users.exceptions.DuplicatedDataException;
import com.example.sisdi_users.exceptions.InconsistencyDataException;
import com.example.sisdi_users.exceptions.NotFoundException;
import com.example.sisdi_users.usermanagement.model.UserJPA;

import com.example.sisdi_users.usermanagement.service.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;
import com.example.sisdi_users.usermanagement.api.proto.UserRequests.AuthRequest;
import com.example.sisdi_users.usermanagement.api.proto.UserRequests.CreateUserRequest;
import com.example.sisdi_users.usermanagement.api.proto.UserRequests.CreateSubscriptionRequest;
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
    public UserJPA create(UserJPA userJPA, CreateSubscriptionRequest request) throws Exception {
        final var userOpt = dbRepository.findByUsername(userJPA.getUsername());
        if(userOpt.isEmpty()){ //nao existe "neste"
            if(httpRepository.getByUsername(userJPA.getUsername()) == null){ //nao existe "no outro"
                if (request != null) {
                    if (subscriptionRepository.postSubscription(request) == HttpStatus.SC_CREATED) {
                        return dbRepository.save(userJPA);
                    }
                }
                return dbRepository.save(userJPA);
            }
        }
        throw new DuplicatedDataException(UserJPA.class, userJPA.getUsername());
    }

    @Override
    public UserJPA findByUsername (String username, boolean internal) throws Exception{
        Optional<UserJPA> userBD = dbRepository.findByUsername(username);

        if (userBD.isPresent()) {
            return userBD.get();
        }

        if(!internal) {
            UserJPA userJPAHttp = httpRepository.getByUsername(username);
            if (userJPAHttp != null) {
                return userJPAHttp;
            }
        }


        throw new NotFoundException(UserJPA.class,username);
    }

    @Override
    public String login(AuthRequest request, boolean internal) throws Exception {

        Optional<UserJPA> userDB = dbRepository.findByUsername(request.getUsername());
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

            final UserJPA userJPA = (UserJPA) authentication.getPrincipal();

            final Instant now = Instant.now();
            final long expiry = 36000L;

            final String scope = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                    .collect(joining(" "));

            final JwtClaimsSet claims = JwtClaimsSet.builder().issuer("example.io").issuedAt(now)
                    .expiresAt(now.plusSeconds(expiry)).subject(format("%s,%s", userJPA.getId(), userJPA.getUsername()))
                    .claim("roles", scope).build();

            final String token = this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

            return token;
        } catch (final BadCredentialsException ex) {
            throw new InconsistencyDataException(request.getUsername());
        }
    }



}
