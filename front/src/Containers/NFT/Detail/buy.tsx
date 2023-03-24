import { ButtonDiv } from 'component/button/Button'
import { NftSearchDetail } from 'Store/type/NftSearchDetail'
import styles from './Buy.module.scss'

export function BuyDiv(props: { data: number }) {
  const price = props.data
  return (
    <div className={styles.container}>
      <div className={styles.subText}>Current Price</div>
      <div className={styles.price}>{price} O₂</div>
      <div style={{ display: 'flex' }}>
        <ButtonDiv text={'Buy now'} icon={'Buy'} />
        <ButtonDiv text={'List'} color={'white'} icon={'List'} />
      </div>
    </div>
  )
}