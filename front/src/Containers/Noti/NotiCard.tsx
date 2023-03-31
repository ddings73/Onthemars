import styles from './NotiCard.module.scss';
import ClearIcon from '@mui/icons-material/Clear';

function NotiCard() {
  const handleToDelete = () => {
    alert('삭제하시겠습니까?');
    //api delete 연결하기
  };
  return (
    <div className={styles.container}>
      <div className={styles.delete} onClick={handleToDelete}>
        <ClearIcon sx={{ color: '#7E7F83' }} fontSize="small" />
      </div>
      <div className={styles.titleContainer}>
        <div className={styles.title}>제목</div>
        <div className={styles.date}>03월 31일 12:11</div>
      </div>
      <div className={styles.content}>내용</div>
    </div>
  );
}

export default NotiCard;
