import { NftSearchDetail } from 'Store/type/NftSearchDetail';
import styles from './NftDetailNameInfo.module.scss';
import Check from 'assets/nftDetail/check.png'
import View from 'assets/nftDetail/view.png'
import { faHeart } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { useEffect, useState } from 'react';
import Swal from 'sweetalert2';

export function NftDetailNameInfo(props: { detaildata: NftSearchDetail }) {
  const [isLike, setisLike] = useState(false)

  const infoData = props.detaildata

  useEffect(() => {
    setisLike(infoData.isFavorite)
  }, [infoData.isFavorite])


  return (
    <div className={styles.container}>
      <div className={styles.check}>
        <div>{infoData.cropParent} NFT</div>
        <img className={styles.icon} src={Check} alt="" />
      </div>
      <div className={styles.title}>
        <div >{infoData.nftName}</div>
        <div onClick={() => {
          // if (localStorage.getItem('token')) {
          setisLike((prev) => !prev)
          // } else {
          // Swal.fire('로그인 후 사용해 주세요.', '', 'error');
          // }
        }}>
          {isLike ?
            <FontAwesomeIcon icon={faHeart} className={styles.heart} /> :
            <FontAwesomeIcon icon={faHeart} />
          }
        </div>
      </div>
      <div className={styles.flexDiv}>
        <div>Owned by </div>
        <div className={styles.light}>{infoData.ownerNickname}</div>
      </div>
      <div className={styles.flexDiv}>
        <img className={styles.icon} src={View} alt="" />
        <div style={{ margin: '0 0.5em' }}>{infoData.viewCnt}</div>
        <div> views</div>
      </div>
    </div>
  );
}