import React from 'react';
import PuffLoader from 'react-spinners/FadeLoader';

function Loading() {
  return (
    <div className="contentWrap">
      <div
        style={{
          position: 'fixed',
          top: '50%',
          left: '50%',
          transform: 'translate(-50%, -50%)',
        }}
      >
        <PuffLoader color="#ffffff" height={15} width={5} radius={2} margin={2} />
      </div>
    </div>
  );
}

export default Loading;
