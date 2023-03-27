package onthemars.back.nft.repository;

import java.util.List;
import onthemars.back.nft.entity.Nft;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NftRepository extends PagingAndSortingRepository<Nft, String> {

    List<Nft> findByTypeAndActivated(String type, Boolean activated);

}
