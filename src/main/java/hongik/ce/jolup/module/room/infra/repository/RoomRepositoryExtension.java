package hongik.ce.jolup.module.room.infra.repository;

import hongik.ce.jolup.module.room.domain.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface RoomRepositoryExtension {
    Page<Room> findByKeyword(String keyword, Pageable pageable);

}
