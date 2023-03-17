import styles from './card.module.scss';

interface CardProps {
  size: string;
}

export function Card({ size }: CardProps) {

  const img_address = 'https://f1.tokenpost.kr/2021/12/p9f2wvlf7b.jpg'
  return (
    <div className={styles[`${size}`]}>
      <img
        src={img_address}
        alt=""
      />
    </div>

  );
}

export default Card;
