import styles from './Button.module.scss';
import Buy from 'assets/nftDetail/buy.png'
import List from 'assets/nftDetail/list.png'
import { Button } from 'antd';
import { LoadingOutlined } from '@ant-design/icons';


interface Props {
  text?: string;
  color?: string;
  icon?: string;
  disabled?: boolean;
  loading?: boolean;
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
            {props.loading ?
              <LoadingOutlined className={styles.icon} spin /> :
              <div>{props.text}</div>
            }
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
            {props.loading ?
              <LoadingOutlined className={styles.icon} spin /> :
              <div>{props.text}</div>
            }
          </Button>
        }</>

      }
    </>
  )
}