package onthemars.back.user.service;

import lombok.RequiredArgsConstructor;
import onthemars.back.user.repository.ProfileRepository;
import onthemars.back.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;


}
