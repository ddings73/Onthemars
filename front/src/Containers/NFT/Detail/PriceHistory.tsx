import styles from './PriceHistory.module.scss';
import React from 'react';
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

ChartJS.register(CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend);

export const options = {
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
      beginAtZero: true,
      max: 9,
      ticks: {
        stepSize: 3,
      },
    },
  },
};

const labels = ['January', 'February', 'March', 'April', 'May', 'June', 'July'];

export const data = {
  labels,
  datasets: [
    {
      label: 'Test 1',
      data: [1, 3, 6, 8, 3, 6, 1],
      borderColor: '#00BD13',
      // backgroundColor: '#00BD13',
    },
  ],
};

export function PriceHistory() {
  return (
    <div className={styles.container}>
      <div>
        <img className={styles.icon} src={pricehi} alt="" />
        Price History
      </div>
      <Line options={options} data={data} />
    </div>
  );
}
