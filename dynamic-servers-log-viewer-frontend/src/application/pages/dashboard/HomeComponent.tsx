import React from "react";
import {Card} from "antd";
import Centered from "../../components/Centered";
import RunningServersContainer from "./running/RunningServersContainer";
import StatisticsContainer from "./stats/StatisticsContainer";

type ItemListProps = {};

const HomeComponent: React.FC<ItemListProps> = () => {
  return <Centered style={{marginTop: 25}}>
    <StatisticsContainer/>
    <Card style={{marginTop: 30, width: '100vh', boxShadow: 'none'}} bordered={false}>
      <div>
        <h2>Running Servers</h2>
        <RunningServersContainer/>
      </div>
    </Card>
  </Centered>
}

export default HomeComponent;