import React, { useRef, useState } from 'react';
import { useTexture } from '@react-three/drei';
import { useSpring, animated, config } from '@react-spring/three';
import planet from 'assets/p.jpg';
import { api } from 'apis/api/ApiController';

interface Sphere1Props {
  position: [number, number, number];
}

function Sphere1(props: Sphere1Props) {
  const imgMap = useTexture(planet);

  const ref = useRef<THREE.Mesh>(null);
  const [hovered, hover] = useState(false);
  const [clicked, click] = useState(false);

  // const randomNumber = Math.floor(Math.random() * 10);
  const { scale } = useSpring({
    scale: hovered ? 1.5 : 1,
    config: config.wobbly,
  });

  const colors = [
    '#FD8A8A',
    '#FEBE8C',
    '#FFF6BD',
    '#C4DFAA',
    '#AEE2FF',
    '#8EA7E9',
    '#DBC6EB',
    '#FEDEFF',
    '#C8B6A6',
  ];
  const [color, setColor] = useState(() => `${colors[Math.floor(Math.random() * colors.length)]}`);

  const [address, setAddress] = useState<string>('');
  const handleClick = () => {
    setColor(`white`);
    click(!clicked);
    getAddress();
  };

  const getAddress = async () => {
    await api
      .get('/farm/random', {
        headers: { Authorization: sessionStorage.getItem('accessToken') },
      })
      .then((res) => {
        setAddress(res.data.address);
      });
  };

  return (
    <>
      <animated.mesh
        {...props}
        ref={ref}
        scale={scale}
        onClick={handleClick}
        onPointerOver={() => hover(true)}
        onPointerOut={() => hover(false)}
      >
        <sphereGeometry args={[1.3, 50, 25]} />
        <meshStandardMaterial attach="material" map={imgMap} color={color} />
      </animated.mesh>
    </>
  );
}

export default Sphere1;
