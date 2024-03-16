import React from "react";
import {DashboardStatistics} from "./DashboardStatistics";
import {Card, Col, Row, Statistic} from "antd";

type ItemListProps = {
  stats: DashboardStatistics
};

const StatisticsComponent: React.FC<ItemListProps> = ({stats}) => {
  return <Row gutter={16}>
    <Col span={6}>
      <Card bordered={false}>
        <Statistic
          title="Today saved"
          value={stats.todayLogs}
        />
      </Card>
    </Col>
    <Col span={6}>
      <Card bordered={false}>
        <Statistic
          title="Last 30 days saved"
          value={stats.lastThirtyDaysLogs}
        />
      </Card>
    </Col>
    <Col span={6}>
      <Card bordered={false}>
        <Statistic
          title="Total saved"
          value={stats.totalLogs}
        />
      </Card>
    </Col>
    <Col span={6}>
      <Card bordered={false}>
        <Statistic
          title="Log-File retention"
          value={stats.deleteLogsAfterDays + " days"}
        />
      </Card>
    </Col>
  </Row>
}

export default StatisticsComponent;