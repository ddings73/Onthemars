import React from 'react';
import styles from './FarmContainer.module.scss';
import { Canvas } from '@react-three/fiber';
import { OrbitControls, Stars, PerspectiveCamera } from '@react-three/drei';
import Sphere from './Sphere';

function repeatSphere(props: any): JSX.Element[] {
  let arr: JSX.Element[] = [];
  for (let i = 0; i < 50; i++) {
    arr.push(
      <Sphere
        key={i}
        position={[Math.random() * 50 - 25, Math.random() * 50 - 25, Math.random() * 50 - 25]}
        setIsMyFarm={props.setIsMyFarm}
        isMyFarm={props.isMyFarm}
        setFarmAddress={props.setFarmAddress}
        farmAddress={props.farmAddress}
      />,
    );
  }
  return arr;
}

function FarmContainer(props: any): JSX.Element {
  return (
    <>
      <div className={styles.universe}>
        <Canvas camera={{ position: [0, 10, 10], fov: 80 }}>
          <PerspectiveCamera makeDefault position={[5, -20, 0]} />
          <OrbitControls autoRotate={false} />
          <ambientLight intensity={1} />
          <pointLight intensity={1} position={[10, 15, 10]} />
          <Stars />
          <Sphere
            position={[0, 0, 0]}
            setIsMyFarm={props.setIsMyFarm}
            isMyFarm={props.isMyFarm}
            setFarmAddress={props.setFarmAddress}
            farmAddress={props.farmAddress}
          />
          {repeatSphere(props)}
        </Canvas>
      </div>
    </>
  );
}

export default FarmContainer;
