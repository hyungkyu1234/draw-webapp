package hongik.ce.jolup.repository;

import hongik.ce.jolup.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findAllById(Iterable<Long> longs);

    List<Member> findByEmailIn(Collection<String> emails);
    Optional<Member> findByEmail(String email);
    Optional<Member> findByName(String name);
    boolean existsByEmail(String email);
}
