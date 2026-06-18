package com.anudari.admin.serviceImpl;

import com.anudari.admin.constant.Headers;
import com.anudari.admin.dto.UserResponse;
import com.anudari.admin.service.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private static final String USER_SERVICE_URL = "lb://user-service/api/users";

    private final RestTemplate restTemplate;

    @Value("${app.internal-secret}")
    private String internalSecret;

    @Override
    public List<UserResponse> listAllUsers() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(Headers.INTERNAL_SECRET, internalSecret);

        ResponseEntity<List<UserResponse>> response = restTemplate.exchange(
                USER_SERVICE_URL,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {});

        return response.getBody();
    }
}
