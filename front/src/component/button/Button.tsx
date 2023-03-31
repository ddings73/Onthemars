import styles from './Button.module.scss';
import Buy from 'assets/nftDetail/buy.png'
import List from 'assets/nftDetail/list.png'
import { Button } from 'antd';


interface Props {
  text?: string;
  color?: string;
  icon?: string;
  disabled?: boolean;
}

export function ButtonDiv(props: Props) {

  return (
    <>
      {props.color === 'white' ?
        <>{props.disabled === true ?
          <Button type="text" disabled className={styles.white}>
            {props.icon === 'List' ?
              <img className={styles.icon} src={List} alt="" />
              : <></>}
            <div>{props.text}</div>
          </Button> : <Button className={styles.white}>
            {props.icon === 'List' ?
              <img className={styles.icon} src={List} alt="" />
              : <></>}
            <div>{props.text}</div>
          </Button>
        }</> :
        <>{props.disabled === true ?
          <Button type="text" disabled className={styles.green}>
            {props.icon === 'Buy' ?
              <img className={styles.icon} src={Buy} alt="" />
              : <></>}
            <div>{props.text}</div>
          </Button> :
          <Button className={styles.green}>
            {props.icon === 'Buy' ?
              <img className={styles.icon} src={Buy} alt="" />
              : <></>}
            <div>{props.text}</div>
          </Button>
        }</>

      }
    </>
  )
}