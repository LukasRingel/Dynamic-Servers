import React from "react";
import {Card} from "antd";
import Centered from "../../components/Centered";
import {FoundEntity} from "./FoundEntity";
import FoundLogsTable from "./result/FoundLogsTable";
import FilterComponent from "./filter/FilterComponent";

type ItemListProps = {};

const SearchPage: React.FC<ItemListProps> = (props) => {
  const [foundLogs, setFoundLogs] =
    React.useState<FoundEntity[]>([]);

  return <Centered style={{marginTop: 25}}>
    <Card style={{marginTop: 30, width: '100vh', boxShadow: 'none'}} bordered={false}>
      <div style={{marginBottom: 30}}>
        <h3>Log search</h3>
        <span>Search logs of stopped servers with the applied filters</span>
      </div>
      <FilterComponent updateEntities={(entities) => setFoundLogs(entities)}/>
    </Card>
    <Card style={{marginTop: 30, width: '100vh', boxShadow: 'none'}} bordered={false}>
      <FoundLogsTable entities={foundLogs}/>
    </Card>
  </Centered>
}

export default SearchPage;