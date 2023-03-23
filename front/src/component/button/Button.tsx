import styles from './Button.module.scss';

interface Props {
  text?: string;
  color?: string;
}

export function ButtonDiv(props: Props) {
  console.log(props);

  return (
    <>
      {props.color === 'white' ?
        <div className={styles.white}>{props.text}</div> :
        <div className={styles.green}>{props.text}</div>
      }
    </>
  )
}