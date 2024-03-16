import React from "react";
import {Button, Table, TableProps} from "antd";
import {FoundEntity} from "../FoundEntity";
import {useNavigate} from "react-router-dom";

type ItemListProps = {
  entities: FoundEntity[];
};

const FoundLogsTable: React.FC<ItemListProps> = ({entities}) => {
  const navigate = useNavigate();

  const columns: TableProps<FoundEntity>['columns'] = [
    {
      title: 'Id',
      dataIndex: 'serverId',
      key: 'id'
    },
    {
      title: 'Template',
      dataIndex: 'template',
      key: 'template',
      sorter: (a, b) => a.template.localeCompare(b.template)
    },
    {
      title: 'Executor',
      dataIndex: 'executorId',
      key: 'executor',
      sorter: (a, b) => a.executorId.localeCompare(b.executorId)
    },
    {
      title: 'Created At',
      dataIndex: 'createdAt',
      key: 'createdAt',
      render: (createdAt: FoundEntity['createdAt']) => {
        return <span>{new Date(createdAt).toLocaleString()}</span>
      }
    },
    {
      title: 'Stopped At',
      dataIndex: 'stoppedAt',
      key: 'stoppedAt',
      render: (stoppedAt: FoundEntity['stoppedAt']) => {
        return <span>{new Date(stoppedAt).toLocaleString()}</span>
      }
    },
    {
      title: 'Action',
      key: 'action',
      render: (_, record) => (
        <Button type={'primary'} onClick={() => navigate("/view/stopped/" + record.serverId)}>Open Log</Button>
      ),
    }
  ]

  return <Table columns={columns} dataSource={entities}></Table>
}

export default FoundLogsTable;