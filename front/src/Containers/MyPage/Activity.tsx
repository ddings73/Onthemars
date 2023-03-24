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
import styles from './Activity.module.scss';

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
    },
    y: {
      grid: {
        color: '#7E7F83',
      },
      beginAtZero: true,
      max: 9,
    },
  },
};

const labels = ['January', 'February', 'March', 'April', 'May', 'June', 'July'];

export const data = {
  labels,
  datasets: [
    {
      label: 'Test 1',
      data: [2, 1, 4, 7, 5, 3, 6],
      borderColor: '#00BD13',
      backgroundColor: '#00BD13',
    },
  ],
};

function Activity() {
  return (
    <div className={styles.container}>
      <h2>Line Example</h2>
      <Line options={options} data={data} />
    </div>
  );
}

export default Activity;
