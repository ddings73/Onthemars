import styles from './card.module.scss';

interface CardProps {
  size: string;
  img_address: string;
}

export function Card({ size, img_address }: CardProps) {
  // console.log(img_address);
  return (
    <div className={styles[`${size}`]}>
      <img src={img_address} alt="" />
    </div>
  );
}

export default Card;
