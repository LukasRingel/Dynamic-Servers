import React from "react";
import {RunningServer} from "./model/RunningServer";
import {Button, Table, TableProps, Tag} from "antd";
import {colorMap, textMap} from "./renderer/StatusTagRenderer";
import {useNavigate} from "react-router-dom";

type ItemListProps = {
  runningServers: RunningServer[];
};

const RunningServersList: React.FC<ItemListProps> = (props) => {
  const navigate = useNavigate();

  const columns: TableProps<RunningServer>['columns'] = [
    {
      title: 'Id',
      dataIndex: 'id',
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
      dataIndex: 'executor',
      key: 'executor',
      sorter: (a, b) => a.executor.localeCompare(b.executor)
    },
    {
      title: 'Status',
      dataIndex: 'status',
      key: 'status',
      render: (status: RunningServer['status']) => {
        return <Tag color={colorMap[status]}>
          {textMap[status]}
        </Tag>
      },
      sorter: (a, b) => a.status.localeCompare(b.status)
    },
    {
      title: 'Created At',
      dataIndex: 'createdAt',
      key: 'createdAt',
      render: (createdAt: RunningServer['createdAt']) => {
        return <span>{new Date(createdAt).toLocaleString()}</span>
      }
    },
    {
      title: 'Tags',
      dataIndex: 'tags',
      key: 'tags',
      render: (tags: RunningServer['tags']) => {
        return tags.map(tag => {
          return <Tag key={tag}>{tag}</Tag>
        })
      },
      sorter: (a, b) => a.tags.join().localeCompare(b.tags.join())
    },
    {
      title: 'Action',
      key: 'action',
      render: (text, record) => (
        <Button type={'primary'} onClick={() => navigate("view/live/" + record.id)}>Open Log</Button>
      ),
    }
  ]

  return <Table columns={columns} dataSource={props.runningServers}></Table>
}

export default RunningServersList;