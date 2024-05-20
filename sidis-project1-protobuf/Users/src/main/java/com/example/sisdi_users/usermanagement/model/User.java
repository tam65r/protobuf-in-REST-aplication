package com.example.sisdi_users.usermanagement.model;


import javax.persistence.*;
import javax.validation.constraints.*;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;


@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User  implements UserDetails  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, updatable = false)
    @NotNull
    @NotBlank
    @Size(min = 1, max = 32)
    private String name;

    @Setter
    @Getter
    private boolean enabled = true;

    @Column(nullable = false)
    private  String citizenCardNumber;

    @Column(nullable = false)
    private LocalDateTime birthday;

    @Column(nullable = false)
    private  String phoneNumber;
    @Column(nullable = false)
    private  Gender sex;

    @Column(unique = true, updatable = false, nullable = false)
    @Email
    @Getter
    @NotNull
    @NotBlank
    private String username;

    @Column(nullable = false)
    @Getter
    @NotNull
    @NotBlank
    private String password;

    @ElementCollection
    @Getter
    private final Set<AuthorityRole> authorities = new HashSet<>();

    // Constructors
    protected User() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User(final String username, final String password) {
        this.username = username;
        setPassword(password);
    }

    public static User newUser(String password, String username, LocalDateTime birthday, String name, String citizenCardNumber, String phoneNumber,
                               Gender sex, String role) {
        User u = new User(username,password);
        u.setName(name);
        u.setPhoneNumber(phoneNumber);
        u.setCitizenCardNumber(citizenCardNumber);
        u.setSex(sex);
        u.addAuthority(new AuthorityRole(role.toUpperCase()));
        u.setBirthday(birthday);
        return u;
    }

    public void addAuthority(final AuthorityRole r) {
        authorities.add(r);
    }
    // Getters and Setters
    public Long getId() {
        return id;
    }

    private void setName(final String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("'name' is a mandatory attribute of User");
        }
        if (!name.matches("^[a-zA-Z0-9_\\s-]+$")) {
            throw new IllegalArgumentException("Invalid character(s) in 'name', i.e., only alphanumeric, spaces, underscores, and hyphens are valid");
        }
        this.name = name;
    }

    public void setPassword(final String password) {
        this.password = Objects.requireNonNull(password);
    }
    public String getName() {
        return name;
    }

    public String getCitizenCardNumber() {
        return citizenCardNumber;
    }

    public void setCitizenCardNumber(String citizenCardNumber) {
        if (citizenCardNumber == null || citizenCardNumber.length() != 9) {
            throw new IllegalArgumentException("Invalid NIF!");
        }

        for (char c : citizenCardNumber.toCharArray()) {
            if (!Character.isDigit(c)) {
                throw new IllegalArgumentException("Invalid NIF!");
            }
        }
        this.citizenCardNumber = citizenCardNumber;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        final Pattern PORTUGUESE_PHONE_REGEX = Pattern.compile("^\\+351\\d{9}$|^00\\d{2}\\d{9}$|^9[1236]\\d{7}$|^2\\d{8}$");
        if (!PORTUGUESE_PHONE_REGEX.matcher(phoneNumber).matches()) {
            throw new IllegalArgumentException("Invalid Phone Number " + phoneNumber);
        }
        this.phoneNumber = phoneNumber;
    }

    public Gender getSex() {
        return sex;
    }

    public void setSex(Gender sex) {
        this.sex = sex;
    }


    public String getRole() {
        if (authorities.isEmpty()) {
            return null;
        }

        AuthorityRole firstAuthority = authorities.iterator().next();
        return firstAuthority.getAuthority();
    }

    @Override
    public boolean isAccountNonExpired() {
        return isEnabled();
    }

    @Override
    public boolean isAccountNonLocked() {
        return isEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isEnabled();
    }

}
