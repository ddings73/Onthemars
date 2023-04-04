import Load from 'assets/lotties/lottieLoding.json'
import Lottie from "lottie-react";

export function Loading() {
  return (
    <div style={{ width: "100%" }}>
      <Lottie style={{ width: "200px", margin: '0 auto' }} animationData={Load} />
    </div>
  )
}