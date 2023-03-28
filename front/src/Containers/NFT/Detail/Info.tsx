import styles from './Info.module.scss';
import Attri from 'assets/nftDetail/attri.png'
import Details from 'assets/nftDetail/details.png'

import { useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCaretUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { NftSearchDetail } from 'Store/type/NftSearchDetail';



export function NFTInfo(props: { detaildata: NftSearchDetail }) {
  const infoData = props.detaildata

  const [visibleAttri, setVisibleAttri] = useState(false);
  const [visibleDetails, setVisibleDetails] = useState(false);

  return (
    <div className={styles.container}>
      <div className={styles.titleDiv} onClick={() => setVisibleAttri((prev) => !prev)} >
        <div>
          <img className={styles.icon} src={Attri} alt="" />
          Attributes
        </div>
        <div className={styles.icon}>{
          visibleAttri ?
            <FontAwesomeIcon icon={faSortDown} /> :
            <FontAwesomeIcon icon={faCaretUp} />}
        </div>
      </div>
      {visibleAttri ?
        <></> :
        <div className={styles.boxDiv}>
          {infoData.info.attributes.map((data: any) => (
            <div className={styles.box}>
              <div className={styles.value}>{data.value}</div>
              <div className={styles.data}>{data.data}</div>
            </div>
          ))}
        </div>}
      <div className={styles.titleDiv} onClick={() => setVisibleDetails((prev) => !prev)} >
        <div>
          <img className={styles.icon} src={Details} alt="" />
          Details
        </div>
        <div className={styles.icon}>{
          visibleDetails ?
            <FontAwesomeIcon icon={faSortDown} /> :
            <FontAwesomeIcon icon={faCaretUp} />}
        </div>
      </div>
      {visibleDetails ? <></> :
        <div className={styles.boxDiv2}>
          <div className={styles.infoDiv}>
            <div>Contract Address</div>
            <div className={styles.value}>{infoData.info.address}</div>
          </div>
          <div className={styles.infoDiv}>
            <div>Token ID</div>
            <div className={styles.value}>{infoData.info.tokenId}</div>
          </div>
          <div className={styles.infoDiv}>
            <div>Token Standard</div>
            <div>{infoData.info.tokenStandard}</div>
          </div>
          <div className={styles.infoDiv}>
            <div>Chain</div>
            <div>{infoData.info.chain}</div>
          </div>
          <div className={styles.infoDiv}>
            <div>Last Updated</div>
            <div>{infoData.info.lastUpdate}</div>
          </div>
          <div className={styles.infoDiv}>
            <div>DNA</div>
            <div>{infoData.info.dna}</div>
          </div>
        </div>
      }
    </div>
  );
}