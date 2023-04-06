import { NftSearchDetail } from 'Store/type/NftSearchDetail';
import styles from './NftDetailNameInfo.module.scss';
import Check from 'assets/nftDetail/check.png'
import View from 'assets/nftDetail/view.png'
import { faHeart } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { useState } from 'react';
import Swal from 'sweetalert2';
import axios from 'axios';
import { baseURL } from 'apis/baseApi';
import { Link } from 'react-router-dom';

export function NftDetailNameInfo(props: { detaildata: NftSearchDetail }) {

  const infoData = props.detaildata
  const transactionId = props.detaildata.info.transactionId

  const [isLike, setisLike] = useState(infoData.isFavorite)
  // Like 버튼
  function likeButton() {
    axios({
      method: 'put',
      url: baseURL + `/nft/favorite/${transactionId}`,
      headers: {
        Authorization: sessionStorage.getItem('accessToken'),
      },
    })
  };



  return (
    <div className={styles.container}>
      <div className={styles.check}>
        <div>{infoData.cropParent} NFT</div>
        <img className={styles.icon} src={Check} alt="" />
      </div>
      <div className={styles.title}>
        <div >{infoData.nftName}</div>
        <div onClick={() => {
          if (sessionStorage.getItem('address')) {
            setisLike((prev) => !prev)
            likeButton()
          } else {
            Swal.fire('로그인 후 사용해 주세요.', '', 'error');
          }
        }}>
          {isLike ?
            <FontAwesomeIcon icon={faHeart} className={styles.heart} /> :
            <FontAwesomeIcon icon={faHeart} />
          }
        </div>
      </div>
      <div className={styles.flexDiv}>
        <div>Owned by </div>
        <Link className={styles.light} to={`/mypage/${infoData.ownerAddress}`}>{infoData.ownerNickname}</Link>
      </div>
      <div className={styles.flexDiv}>
        <img className={styles.icon} src={View} alt="" />
        <div style={{ margin: '0 0.5em' }}>{infoData.viewCnt.toLocaleString()}</div>
        <div> views</div>
      </div>
    </div>
  );
}