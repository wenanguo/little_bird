<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <div class="table-page-search-wrapper">
        <a-form layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="时间">
                <a-range-picker @change="createChange" :style="{width: '256px'}" />
              </a-form-item>
            </a-col>
            <template v-if="advanced">
            </template>
            <a-col :md="!advanced && 8 || 24" :sm="24">
              <span class="table-page-search-submitButtons" :style="advanced && { float: 'right', overflow: 'hidden' } || {} ">
                <a-button type="primary" @click="$refs.table.refresh(true)">查询</a-button>
                <a-button style="margin-left: 8px" @click="resetSearchForm">重置</a-button>
              </span>
            </a-col>
          </a-row>
        </a-form>
      </div>

      <div class="table-operator">
        <a-button type="primary" v-action:add icon="plus" @click="handleAdd">新建</a-button>
        <a-dropdown v-action:delete v-if="selectedRowKeys.length > 0">
          <a-menu slot="overlay">
            <a-menu-item key="1" @click="handleconfirmDel"><a-icon type="delete" />删除</a-menu-item>
            <!-- lock | unlock -->
            <a-menu-item key="2"><a-icon type="lock" />锁定</a-menu-item>
          </a-menu>
          <a-button style="margin-left: 8px">
            批量操作 <a-icon type="down" />
          </a-button>
        </a-dropdown>
      </div>

      <s-table
        ref="table"
        size="default"
        rowKey="id"
        :columns="columns"
        :data="loadData"
        :alert="false"
        showPagination="auto"
      >
        <span slot="serial" slot-scope="text, record, index">
          {{ index + 1 }}
        </span>
        <span slot="status" slot-scope="text">
          <a-badge :status="text | statusTypeFilter" :text="text | statusFilter" />
        </span>
        <span slot="action" slot-scope="text, record">
          <template>
            <a v-action:edit @click="handleEdit(record)">修改</a>
            <a-divider type="vertical" />
            <a-popconfirm title="是否要删除当前数据？" @confirm="handleDel(record)">
              <a v-action:delete style="color: red">删除</a>
            </a-popconfirm>
          </template>
        </span>
      </s-table>

    </a-card>
  </page-header-wrapper>
</template>

<script>
    import moment from 'moment'
    import { STable, Ellipsis } from '@/components'
    import { statusMap } from '@/api/RC'
    import { getLbOrdersStatisticsList, saveLbOrdersStatistics, delLbOrdersStatistics, batchDelLbOrdersStatistics } from '@/api/lbOrdersStatistics'

    const columns = [
        {
            title: '支付渠道',
            sorter: true,
            customRender: (value) => channelMap[value].text,
            dataIndex: 'channel'
        }, {
            title: '商品类型',
            sorter: true,
            customRender: (value) => ttypeMap[value].text,
            dataIndex: 'ttype'
        }, {
            title: '设备类型',
            sorter: true,
            customRender: (value) => devTypeMap[value].text,
            dataIndex: 'dev_type'
        }, {
            title: '数量',
            sorter: true,
            dataIndex: 'tcount'
        }
        // , {
        //     title: '金额',
        //     sorter: true,
        //     customRender: (value) => '￥' + parseInt(value),
        //     dataIndex: 'ttotal_amount'
        // }
    ]
    const channelMap = {
      'aliPay': {
          status: 'default',
          text: '支付宝'
      },
      'applePay': {
          status: 'processing',
          text: '苹果内购'
      },
      'wxPay': {
          status: 'processing',
          text: '微信支付'
      }
    }
    const ttypeMap = {
      1: {
          status: 'default',
          text: '点播'
      },
      2: {
          status: 'processing',
          text: '包年'
      }
    }
    const devTypeMap = {
      1: {
          status: 'default',
          text: 'Android'
      },
      2: {
          status: 'processing',
          text: 'IOS'
      }
    }

    export default {
        name: 'TableList',
        components: {
            STable,
            Ellipsis
        },
        data () {
            this.columns = columns
            return {
                // create model
                visible: false,
                createValue: [],
                title: '新增',
                confirmLoading: false,
                mdl: null,
                // 高级搜索 展开/关闭
                advanced: false,
                // 查询参数
                queryParam: {},
                // 加载数据方法 必须为 Promise 对象
                loadData: parameter => {
                    const requestParameters = Object.assign({}, parameter, this.queryParam)
                    // 删除为空字符串属性
                    Object.keys(requestParameters).forEach(item => {
                        if (!requestParameters[item]) delete requestParameters[item]
                    })
                    // 设置获取全部状态
                    if (requestParameters['status'] && requestParameters['status'] === 0) delete requestParameters['status']
                    console.log('loadData request parameters:', requestParameters)
                    return getLbOrdersStatisticsList(requestParameters)
                        .then(res => {
                            return res.result
                        })
                },
                selectedRowKeys: [],
                selectedRows: []
            }
        },
        filters: {
            statusFilter (type) {
                return statusMap[type].text
            },
            statusTypeFilter (type) {
                return statusMap[type].status
            }
        },
        computed: {
            rowSelection () {
                return {
                    selectedRowKeys: this.selectedRowKeys,
                    onChange: this.onSelectChange
                }
            }
        },
        methods: {
          createChange (dates, dateStrings) {
            this.createValue = dates
            this.queryParam.startTime = moment(dateStrings[0] + ' 00:00:00').format('YYYY-MM-DD HH:mm:ss').toString()
            this.queryParam.endTime = moment(dateStrings[1] + ' 23:59:59').format('YYYY-MM-DD HH:mm:ss').toString()
          },
            handleAdd () {
                this.mdl = null
                this.title = '新增'
                this.visible = true
            },
            handleEdit (record) {
                this.title = '修改'
                this.visible = true
                this.mdl = { ...record }
            },
            handleOk () {
                const form = this.$refs.editForm.form
                this.confirmLoading = true
                form.validateFields((errors, values) => {
                    if (!errors) {
                         // 日期格式化
                            values.updateTime = moment(values.updateTime).format('YYYY-MM-DD HH:mm:ss')
                            values.createTime = moment(values.createTime).format('YYYY-MM-DD HH:mm:ss')

                        if (values.id > 0) {
                            // 修改 e.g.

                            saveLbOrdersStatistics(values).then(res => {
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
                            saveLbOrdersStatistics(values).then(res => {
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
            handleconfirmDel () {
                this.$confirm({
                    title: '是否确认要删除选中数据?',
                    onOk: this.handleBatchDel,
                    class: '提示'
                })
            },
            handleBatchDel () {
                batchDelLbOrdersStatistics(this.selectedRowKeys).then(res => {
                    this.confirmLoading = false
                    // 刷新表格
                    this.$refs.table.refresh()

                    this.$message.info('删除成功')
                })
            },
            handleDel (record) {
                if (record.id > 0) {
                    // 修改 e.g.
                    delLbOrdersStatistics(record).then(res => {
                        this.confirmLoading = false
                        // 刷新表格
                        this.$refs.table.refresh()

                        this.$message.info('删除成功')
                    })
                }
            },

            handleCancel () {
                this.visible = false

                const form = this.$refs.editForm.form
                form.resetFields() // 清理表单数据（可不做）
            },
            onSelectChange (selectedRowKeys, selectedRows) {
                this.selectedRowKeys = selectedRowKeys
                this.selectedRows = selectedRows
            },
            toggleAdvanced () {
                this.advanced = !this.advanced
            },
            resetSearchForm () {
                this.queryParam = {
                    date: moment(new Date())
                }
                // 刷新表格
                this.$refs.table.refresh()
            }
        }
    }
</script>
