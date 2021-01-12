package com.zerock.club.security.service;

import com.zerock.club.entity.ClubMember;
import com.zerock.club.repository.ClubMemberRepository;
import com.zerock.club.security.dto.ClubAuthMemberDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ClubUserDetailsService implements UserDetailsService {

    private final ClubMemberRepository clubMemberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<ClubMember> result = clubMemberRepository.findByEmail(username, false);

        if (result.isPresent() != true) {
            throw new UsernameNotFoundException("Check Email or Social");
        }

        ClubMember clubMember = result.get();

        log.info("-------------------------------------");
        log.info(clubMember);

        ClubAuthMemberDTO clubAuthMemberDTO = new ClubAuthMemberDTO(
                clubMember.getEmail(),
                clubMember.getPassword(),
                clubMember.isFromSocial(),
                clubMember.getRoleSet().stream().map(role ->
                    new SimpleGrantedAuthority("ROLE_" + role.name())
                ).collect(Collectors.toSet())
        );

        clubAuthMemberDTO.setName(clubMember.getName());
        clubAuthMemberDTO.setFromSocial(clubAuthMemberDTO.isFromSocial());

        return clubAuthMemberDTO;
    }
}
