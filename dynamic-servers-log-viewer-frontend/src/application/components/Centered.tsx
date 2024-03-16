import React from "react";
import {Col, Row} from "antd";

const Centered = (props: {
  children: string | number | boolean | React.ReactElement<any, string | React.JSXElementConstructor<any>> | Iterable<React.ReactNode> | React.ReactPortal;
  classNames?: string;
  style?: React.CSSProperties;
  span?: number;
}) => {
  return <Row align={'middle'} justify={"space-around"} gutter={[0, 24]} className={props.classNames}
              style={props.style}>
    <Col span={props.span}>
      {props.children}
    </Col>
  </Row>
}

export default Centered;