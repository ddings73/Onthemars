import styles from './NotiCard.module.scss';
import ClearIcon from '@mui/icons-material/Clear';
import { api } from 'apis/api/ApiController';
import { useNavigate } from 'react-router-dom';
import moment from 'moment';
import Swal from 'sweetalert2';

function NotiCard(props: any) {
  const navigate = useNavigate();

  var t1 = props.regDt;
  var t2 = moment();
  var diffTime = {
    day: moment.duration(t2.diff(t1)).days(),
    hour: moment.duration(t2.diff(t1)).hours(),
    minute: moment.duration(t2.diff(t1)).minutes(),
    second: moment.duration(t2.diff(t1)).seconds(),
  };
  if (diffTime.day === 0 && diffTime.hour === 0 && diffTime.minute === 0) {
    t1 = diffTime.second + '초 전';
  } else if (diffTime.day === 0 && diffTime.hour === 0) {
    t1 = diffTime.minute + '분 전';
  } else if (diffTime.day === 0) {
    t1 = diffTime.hour + '시간 전';
  } else if (diffTime.day < 3) {
    t1 = diffTime.day + '일 전';
  } else {
    t1 = moment(t1).format('M[월] DD[일]');
  }

  const handleToRead = () => {
    const title = props.title;
    api
      .put(`/alarms/${props.id}`, {}, { headers: { Authorization: sessionStorage.getItem('accessToken') } })
      .then(() => {
        if (title === 'NFT') {
          navigate(`/mypage/${sessionStorage.getItem('address')}`);
        } else if (title === 'GAME') {
          navigate(`/game/play`);
        }
      });
  };

  const handleToDelete = () => {
    Swal.fire({
      title: '삭제하시겠습니까?',
      showCancelButton: true,
      icon: 'warning',
    }).then((result) => {
      if (result.isConfirmed) {
        api
          .delete(`/alarms/${props.id}`, {
            headers: { Authorization: sessionStorage.getItem('accessToken') },
          })
          .then(() => window.location.reload());
      }
    });
  };

  return (
    <div className={styles.container}>
      <div className={styles.delete} onClick={handleToDelete}>
        <ClearIcon sx={{ color: '#7E7F83' }} fontSize="small" />
      </div>
      <div onClick={handleToRead}>
        <div className={styles.titleContainer}>
          <div className={props.verified ? styles.readTitle : styles.title}>{props.title}</div>
          <div className={styles.date}>{t1}</div>
        </div>
        <div className={props.verified ? styles.readContent : styles.content}>{props.content}</div>
      </div>
    </div>
  );
}

export default NotiCard;
