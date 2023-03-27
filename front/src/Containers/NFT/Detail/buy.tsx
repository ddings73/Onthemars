import { Input, Modal } from 'antd';
import { ButtonDiv } from 'component/button/Button'
import { useState } from 'react';
import { NftSearchDetail } from 'Store/type/NftSearchDetail'
import styles from './Buy.module.scss'

export function BuyDiv(props: { data: number }) {
  const price = props.data
  const [isModalOpen, setIsModalOpen] = useState(false);

  const showModal = () => {
    setIsModalOpen(true);
  };
  const cancelModal = () => {
    setIsModalOpen(false);
  };
  const [isListModalOpen, setIsListModalOpen] = useState(false);

  const showListModal = () => {
    setIsListModalOpen(true);
  };
  const cancelListModal = () => {
    setIsListModalOpen(false);
  };

  const [listPrice, setListPrice] = useState('')

  function listData(listPrice: string) {
    setIsListModalOpen(false);
    console.log(listPrice);
  };


  return (
    <div className={styles.container}>
      <div className={styles.subText}>Current Price</div>
      <div className={styles.price}>{price} O₂</div>
      <div style={{ display: 'flex', justifyContent: 'space-around' }}>
        <div onClick={showModal} style={{ width: '48%' }}>
          <ButtonDiv text={'Buy now'} icon={'Buy'} />
        </div>
        <div onClick={showListModal} style={{ width: '48%' }}>
          <ButtonDiv text={'List'} color={'white'} icon={'List'} />
        </div>
      </div>
      <Modal open={isModalOpen} centered
        onCancel={cancelModal}
        footer={null}>
        <div className={styles.modalTitle}>구매하시겠습니까?</div>
        <div onClick={cancelModal}><ButtonDiv text={'확인'} /></div>
      </Modal>
      <Modal open={isListModalOpen} centered
        onCancel={cancelListModal}
        footer={null}>
        <div className={styles.modalTitle}>List Price</div>
        <div className={styles.modalINput}>
          <Input id="listPrice" onChange={(e) => setListPrice(e.target.value)} />
          <div className={styles.modalList} onClick={() => listData(listPrice)}><ButtonDiv text={'확인'} /></div>
        </div>
      </Modal>
    </div>
  );
}
