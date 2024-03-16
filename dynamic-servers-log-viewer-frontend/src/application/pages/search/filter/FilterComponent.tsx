import React from "react";
import {FoundEntity} from "../FoundEntity";
import {Button, DatePicker, Form, Input, InputNumber, notification} from "antd";
import {getServersWithFilter} from "./LogSearchDataSource";
import {Moment} from "moment";

type ItemListProps = {
  updateEntities: (entities: FoundEntity[]) => void;
};

type FieldType = {
  template?: string;
  executor?: string;
  before?: Moment;
  after?: Moment;
  limit?: number;
};

const nowMinusOneDay = () => {
  const date = new Date();
  date.setDate(date.getDate() - 1);
  return date;
};

const FilterComponent: React.FC<ItemListProps> = (props) => {
  const [form] = Form.useForm();
  const [api, contextHolder] = notification.useNotification();

  const search = () => {
    form.validateFields().then((values) => {
      getServersWithFilter(
        values.template ? values.template : "",
        values.executor ? values.executor : "",
        values.before ? values.before.toDate() : new Date(),
        values.after ? values.after.toDate() : nowMinusOneDay(),
        values.limit ? values.limit : 10
      ).then((data) => {
        props.updateEntities(data);
      }).catch(() => {
        api.error({
          message: "Error",
          description: "An error occurred while fetching data"
        })
      });
    });
  }

  return <>
    {contextHolder}
    <Form name={"log-search"}
          variant="filled"
          layout={"vertical"}
          form={form}
          initialValues={{amount: 10}}>
      <Form.Item<FieldType>
        label="Template"
        name="template"
        style={{display: 'inline-block', width: 'calc(20% - 8px)'}}
      >
        <Input placeholder={"Any"}/>
      </Form.Item>
      <Form.Item<FieldType>
        label="Executor"
        name="executor"
        style={{display: 'inline-block', width: 'calc(20% - 8px)', margin: '0 8px'}}
      >
        <Input placeholder={"Any"}/>
      </Form.Item>
      <br/>
      <Form.Item name="after" label="After" style={{display: 'inline-block', width: 'calc(20% - 8px)'}}>
        <DatePicker showTime format="YYYY-MM-DD HH:mm:ss"/>
      </Form.Item>
      <Form.Item name="before" label="Before"
                 style={{display: 'inline-block', width: 'calc(50% - 8px)', margin: '0 8px'}}>
        <DatePicker showTime format="YYYY-MM-DD HH:mm:ss"/>
      </Form.Item>
      <Form.Item name={"limit"} label={"Limit"} style={{width: 'calc(50% - 8px)'}}>
        <InputNumber min={1} max={100} defaultValue={10} style={{width: '20%'}}/>
      </Form.Item>
      <Button htmlType={"submit"} type={"primary"} onClick={search}>Search</Button>
    </Form>
  </>
}

export default FilterComponent;