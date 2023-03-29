import styles from './Button.module.scss';
import Buy from 'assets/nftDetail/buy.png'
import List from 'assets/nftDetail/list.png'


interface Props {
  text?: string;
  color?: string;
  icon?: string;
}

export function ButtonDiv(props: Props) {

  return (
    <>
      {props.color === 'white' ?
        <div className={styles.white}>
          {props.icon === 'List' ?
            <img className={styles.icon} src={List} alt="" />
            : <></>}
          <div>{props.text}</div>
        </div> :
        <div className={styles.green}>
          {props.icon === 'Buy' ?
            <img className={styles.icon} src={Buy} alt="" />
            : <></>}
          <div>{props.text}</div>
        </div>
      }
    </>
  )
}