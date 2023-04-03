import React, { useCallback, useEffect, useState } from 'react';
import styles from './UnityContainer.module.scss';
import { Unity, useUnityContext } from 'react-unity-webgl';

function UnityContainer() {
  const [jsonFile, setJsonFile] = useState<JSON>();
  const {
    unityProvider,
    sendMessage,
    addEventListener,
    removeEventListener,
    UNSAFE__detachAndUnloadImmediate: detachAndUnloadImmediate,
  } = useUnityContext({
    loaderUrl: '/Build/Build.loader.js',
    dataUrl: '/Build/Build.data',
    frameworkUrl: '/Build/Build.framework.js',
    codeUrl: '/Build/Build.wasm',
  });

  const reactSetData = useCallback((data: JSON) => {
    setJsonFile(data);
  }, []);

  useEffect(() => {
    addEventListener('CallReact', reactSetData);
    console.log('받은 JSON: ', jsonFile);
    return () => {
      removeEventListener('CallReact', reactSetData);
    };
  }, [addEventListener, removeEventListener, reactSetData]);

  return (
    <>
      <div className={styles.container}>
        <Unity
          className={styles.unityContainer}
          unityProvider={unityProvider}
          style={{ marginTop: '2%', width: '110%', height: '110%' }}
        />
      </div>
    </>
  );
}

export default UnityContainer;
