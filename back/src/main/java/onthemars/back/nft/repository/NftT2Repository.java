package onthemars.back.nft.repository;

import onthemars.back.nft.entity.NftT2;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NftT2Repository extends PagingAndSortingRepository<NftT2, String> {

}
