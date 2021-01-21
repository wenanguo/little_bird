<template>
  <a-card :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form layout="inline">
        <a-row :gutter="48">
          <a-col :md="8" :sm="24">
            <a-form-item label="角色ID">
              <a-input placeholder="请输入"/>
            </a-form-item>
          </a-col>
          <a-col :md="8" :sm="24">
            <a-form-item label="状态">
              <a-select placeholder="请选择" default-value="0">
                <a-select-option value="0">全部</a-select-option>
                <a-select-option value="1">关闭</a-select-option>
                <a-select-option value="2">运行中</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :md="8" :sm="24">
            <span class="table-page-search-submitButtons">
              <a-button type="primary">查询</a-button>
              <a-button style="margin-left: 8px">重置</a-button>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>

    <s-table
      ref="table"
      row-key="id"
      size="default"
      :columns="columns"
      :data="loadData"
      :expandedRowKeys="expandedRowKeys"
      @expand="handleExpand"
    >
      <div
        slot="expandedRowRender"
        slot-scope="record"
        style="margin: 0">
        <a-row
          :gutter="24"
          :style="{ marginBottom: '12px' }">
          <a-col :span="12" v-for="(role, index) in record.permissions" :key="index" :style="{ marginBottom: '12px', height: '23px' }">
            <a-col :lg="6" :md="24">
              <span>{{ role.permissionName }}：</span>
            </a-col>
            <a-col :lg="18" :md="24" v-if="role.actionList && role.actionList.length > 0">
              <a-tag color="cyan" v-for="action in role.actionList" :key="action">{{ action | permissionFilter }}</a-tag>
            </a-col>
            <a-col :span="20" v-else>-</a-col>
          </a-col>
        </a-row>
      </div>
      <a-tag color="blue" slot="status" slot-scope="text">{{ text | statusFilter }}</a-tag>
      <span slot="createTime" slot-scope="text">{{ text | moment }}</span>
      <span slot="action" slot-scope="text, record">
        <a @click="handleEdit(record)">编辑</a>
        <a-divider type="vertical" />
        <a-dropdown>
          <a class="ant-dropdown-link">
            更多 <a-icon type="down" />
          </a>
          <a-menu slot="overlay">
            <a-menu-item>
              <a href="javascript:;">详情</a>
            </a-menu-item>
            <a-menu-item>
              <a href="javascript:;">禁用</a>
            </a-menu-item>
            <a-menu-item>
              <a href="javascript:;">删除</a>
            </a-menu-item>
          </a-menu>
        </a-dropdown>
      </span>
    </s-table>

    <edit-form
        ref="editForm"
        :title="title"
        :visible="visible"
        :loading="confirmLoading"
        :model="mdl"
        :permissions="permissions"
        @cancel="handleCancel"
        @ok="handleOk"
      />

    <!-- <a-modal
      title="操作"
      style="top: 20px;"
      :width="800"
      v-model="visible"
      @ok="handleOk"
    >
      <a-form class="permission-form" :form="form">

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="唯一识别码"
          hasFeedback
          validateStatus="success"
        >
          <a-input
            placeholder="唯一识别码"
            disabled="disabled"
            v-decorator="['id']"
          />
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="角色名称"
          hasFeedback
          validateStatus="success"
        >
          <a-input
            placeholder="角色名字"
            v-decorator="['name']"
          />
        </a-form-item>

        <a-form-item label="状态">
          <a-radio-group v-decorator="['status', { initialValue: 100 }]">
            <a-radio :value="100">正常</a-radio>
            <a-radio :value="101">禁用</a-radio>
          </a-radio-group>
        </a-form-item>

        <a-form-item
          :labelCol="labelCol"
          :wrapperCol="wrapperCol"
          label="描述"
          hasFeedback
        >
          <a-textarea
            :rows="5"
            placeholder="..."
            id="describe"
            v-decorator="['describe']"
          />
        </a-form-item>

        <a-divider>拥有权限</a-divider>
        <template v-for="permission in permissions">
          <a-form-item
            class="permission-group"
            v-if="permission.actionsOptions && permission.actionsOptions.length > 0"
            :labelCol="labelCol"
            :wrapperCol="wrapperCol"
            :key="permission.permissionId"
            :label="permission.permissionName"
          >
            <a-checkbox-group v-decorator="[`permissions.${permission.permissionId}`]" :options="permission.actionsOptions"/>
          </a-form-item>
        </template>

      </a-form>
    </a-modal> -->

  </a-card>
</template>

<script>
import moment from 'moment'
import { STable, Ellipsis } from '@/components'
import { statusMap } from '@/api/RC'
import { getSysPermissionList, saveSysPermission } from '@/api/sysRole'
import EditForm from './sysPermissionForm'
import { PERMISSION_ENUM } from '@/core/permission/permission'

const columns = [
  {
    title: '编号',
    dataIndex: 'id'
  },
  {
    title: '角色名称',
    dataIndex: 'name'
  },
  {
    title: '角色编码',
    dataIndex: 'roleCode'
  },
  {
    title: '状态',
    dataIndex: 'status',
    scopedSlots: { customRender: 'status' }
  },
  {
    title: '操作',
    width: '150px',
    dataIndex: 'action',
    scopedSlots: { customRender: 'action' }
  }
]

export default {
  name: 'TableList',
  components: {
            STable,
            Ellipsis,
            EditForm
        },
  data () {
    return {
      description: '列表使用场景：后台管理中的权限管理以及角色管理，可用于基于 RBAC 设计的角色权限控制，颗粒度细到每一个操作类型。',
      confirmLoading: false,
      visible: false,
      labelCol: {
        xs: { span: 24 },
        sm: { span: 5 }
      },
      wrapperCol: {
        xs: { span: 24 },
        sm: { span: 16 }
      },
      title: '新增',
      permissions: [],
      mdl: null,
      // 高级搜索 展开/关闭
      advanced: false,
      // 查询参数
      queryParam: {},
      // 表头
      columns,
      // 加载数据方法 必须为 Promise 对象
      loadData: parameter => {
        return getSysPermissionList(parameter)
          .then(res => {
            console.log('getRoleList', res)
            // 展开全部行
            // this.expandedRowKeys = res.result.data.map(item => item.id)
            return res.result
          })
      },

      expandedRowKeys: [],
      selectedRowKeys: [],
      selectedRows: []
    }
  },
  filters: {
    statusFilter (key) {
      return statusMap[key].text
    },
    permissionFilter (key) {
      const permission = PERMISSION_ENUM[key]
      return permission && permission.label
    }
  },
  created () {
    // getServiceList().then(res => {
    //   console.log('getServiceList.call()', res)
    // })

    // getRoleList().then(res => {
    //   console.log('getRoleList.call()', res)
    // })
  },
  methods: {
    handleAdd () {
                this.mdl = null
                this.title = '新增'
                this.visible = true
            },
    handleEdit (record) {
      this.title = '修改'
      this.visible = true
      this.mdl = { ...record }

      console.log('record', record)

      const checkboxGroup = {}
      this.permissions = record.permissions.map(permission => {
        const groupKey = `permissions.${permission.permissionId}`
        checkboxGroup[groupKey] = permission.actionList
        const actionsOptions = permission.actionEntitySet.map(action => {
          return {
            label: action.describe,
            value: action.action,
            defaultCheck: action.defaultCheck
          }
        })
        return {
          ...permission,
          actionsOptions
        }
      })

      // this.$nextTick(() => {
      //   console.log('permissions', this.permissions)
      //   console.log('checkboxGroup', checkboxGroup)

      //   this.form.setFieldsValue(pick(record, ['id', 'status', 'describe', 'name']))
      //   this.form.setFieldsValue(checkboxGroup)
      // })
    },
    handleOk (e) {
        const form = this.$refs.editForm.form
        this.confirmLoading = true
        form.validateFields((errors, values) => {
          if (!errors) {
                         // 日期格式化
                            values.createTime = moment(values.createTime).format('YYYY-MM-DD HH:mm:ss')
                            values.updateTime = moment(values.updateTime).format('YYYY-MM-DD HH:mm:ss')
                            // values.permissions.filter(function (s) {
                            //         return s && s.trim()
                            //     })

                        if (values.id > 0) {
                            // 修改 e.g.

                            saveSysPermission(values).then(res => {
                                this.visible = false
                                this.confirmLoading = false
                                // 重置表单数据
                                form.resetFields()
                                // 刷新表格
                                this.$refs.table.refresh()

                                this.$message.info('修改成功')
                            })
                        } else {
                            // 新增
                            saveSysPermission(values).then(res => {
                                this.visible = false
                                this.confirmLoading = false
                                // 重置表单数据
                                form.resetFields()
                                // 刷新表格
                                this.$refs.table.refresh()

                                this.$message.info('新增成功')
                            })
                        }
                    } else {
                        this.confirmLoading = false
                    }
        })
    },
    handleCancel () {
                this.visible = false

                const form = this.$refs.editForm.form
                form.resetFields() // 清理表单数据（可不做）
            },
    onChange (selectedRowKeys, selectedRows) {
      this.selectedRowKeys = selectedRowKeys
      this.selectedRows = selectedRows
    },
    handleExpand (expanded, record) {
      console.log('expanded', expanded, record)
      if (expanded) {
        this.expandedRowKeys.push(record.id)
      } else {
        this.expandedRowKeys = this.expandedRowKeys.filter(item => record.id !== item)
      }
    },
    toggleAdvanced () {
      this.advanced = !this.advanced
    }
  },
  watch: {
    /*
      'selectedRows': function (selectedRows) {
        this.needTotalList = this.needTotalList.map(item => {
          return {
            ...item,
            total: selectedRows.reduce( (sum, val) => {
              return sum + val[item.dataIndex]
            }, 0)
          }
        })
      }
      */
  }
}
</script>

<style lang="less" scoped>
.permission-form {
  /deep/ .permission-group {
    margin-top: 0;
    margin-bottom: 0;
  }
}

</style>
