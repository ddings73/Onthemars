import styles from './PriceHistory.module.scss';
import { useEffect, useState } from 'react';
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
} from 'chart.js';
import { Line } from 'react-chartjs-2';
import pricehi from 'assets/nftDetail/pricehi.png';
import { api } from 'apis/api/ApiController';
import moment from 'moment';

ChartJS.register(CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend);

export function PriceHistory(props: { transactionId: number }) {
  const transactionId = props.transactionId;
  const [chartData, setChartData] = useState([]);

  useEffect(() => {
    api.get(`/nft/graph/${transactionId}`).then((res) => {
      setChartData(res.data);
    });
  }, []);

  const options = {
    responsive: true,
    plugins: {
      legend: {
        position: 'top' as const,
      },
    },
    backgroundColor: 'white',
    scales: {
      x: {
        grid: {
          display: false,
        },
        title: {
          display: true,
          text: 'Month',
        },
      },
      y: {
        grid: {
          color: '#7E7F83',
        },
        border: {
          color: '#7E7F83',
        },
        beginAtZero: true,
        // max: 9,
        ticks: {
          stepSize: 3,
        },
      },
    },
  };

  const labels: string[] = [];
  chartData.map((item: any) => {
    labels.push(moment(item.date).format('YYYY-MM-DD'));
  });
  const price: string[] = [];
  chartData.map((item: any) => {
    price.push(item.price);
  });

  const data = {
    labels,
    datasets: [
      {
        label: 'Price',
        data: price,
        borderColor: '#00BD13',
      },
    ],
  };
  return (
    <div className={styles.container}>
      <div className={styles.title}>
        <img className={styles.icon} src={pricehi} alt="" />
        Price History
      </div>
      <div className={styles.chart}>
        <Line options={options} data={data} />
      </div>
    </div>
  );
}
