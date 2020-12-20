<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <div class="table-page-search-wrapper">
        <a-form layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="名称">
                <a-input v-model="queryParam.subject" placeholder=""/>
              </a-form-item>
            </a-col>
            <a-col :md="8" :sm="24">
              <a-form-item label="使用状态">
                <a-select v-model="queryParam.status" placeholder="请选择" default-value="0">
                  <a-select-option value="0">全部</a-select-option>
                  <a-select-option value="100">正常</a-select-option>
                  <a-select-option value="101">禁用</a-select-option>
                </a-select>
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

      <s-table
        ref="table"
        size="default"
        rowKey="id"
        :columns="columns"
        :data="loadData"
        :alert="false"
        :scroll="{ x: 2500}"
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
            <a @click="handleEdit(record)">退款</a>
            <a-divider type="vertical" />
            <a @click="handleEdit(record)">详情</a>
          </template>
        </span>
      </s-table>

      <edit-form
        ref="editForm"
        :title="title"
        :visible="visible"
        :loading="confirmLoading"
        :model="mdl"
        @cancel="handleCancel"
        @ok="handleOk"
      />
    </a-card>
  </page-header-wrapper>
</template>

<script>
    import moment from 'moment'
    import { STable, Ellipsis } from '@/components'
    import { statusMap } from '@/api/RC'
    import { getLbPayOrderList, saveLbPayOrder, delLbPayOrder, batchDelLbPayOrder } from '@/api/lbPayOrder'
    import EditForm from './lbPayOrderForm'

    const columns = [
        // {
        //     title: 'id',
        //     sorter: true,
        //     width: '80px',
        //     fixed: 'left',
        //     dataIndex: 'id'
        // },
        {
            title: '商品标题',
            sorter: true,
            fixed: 'left',
            width: '150px',
            dataIndex: 'subject'
        }, {
            title: '商品描述',
            sorter: true,
            width: '150px',
            dataIndex: 'body'
        }, {
            title: '金额',
            sorter: true,
            width: '100px',
            dataIndex: 'totalAmount'
        },
        // {
        //     title: '创建订单相应',
        //     sorter: true,
        //     dataIndex: 'tradeAppPayResponse'
        // },
        {
            title: '支付宝交易号',
            sorter: true,
            width: '150px',
            dataIndex: 'tradeNo'
        }, {
            title: '商户订单号',
            sorter: true,
            width: '150px',
            dataIndex: 'outTradeNo'
        }, {
            title: '收款支付宝用户号',
            sorter: true,
            width: '150px',
            dataIndex: 'sellerId'
        }, {
            title: '收款支付宝账号',
            sorter: true,
            width: '150px',
            dataIndex: 'sellerEmail'
        }, {
            title: '商户原始订单号',
            sorter: true,
            width: '150px',
            dataIndex: 'merchantOrderNo'
        }, {
            title: '买家支付宝用户号',
            sorter: true,
            width: '150px',
            dataIndex: 'buyerId'
        }, {
            title: '买家支付宝账号',
            sorter: true,
            width: '150px',
            dataIndex: 'buyerLogonId'
        }, {
            title: '交易状态',
            sorter: true,
            width: '150px',
            dataIndex: 'tradeStatus'
        }, {
            title: '实收金额',
            sorter: true,
            width: '100px',
            dataIndex: 'receiptAmount'
        }, {
            title: '开票金额',
            sorter: true,
            width: '100px',
            dataIndex: 'invoiceAmount'
        }, {
            title: '付款金额',
            sorter: true,
            width: '100px',
            dataIndex: 'buyerPayAmount'
        }, {
            title: '集分宝金额',
            sorter: true,
            width: '100px',
            dataIndex: 'pointAmount'
        }, {
            title: '总退款金额',
            sorter: true,
            width: '100px',
            dataIndex: 'refundFee'
        }, {
            title: '交易创建时间',
            sorter: true,
            width: '100px',
            customRender: (text) => text ? moment(text).format('YYYY-MM-DD HH:mm') : '',
            dataIndex: 'gmtCreate'
        }, {
            title: '交易付款时间',
            sorter: true,
            width: '100px',
            customRender: (text) => text ? moment(text).format('YYYY-MM-DD HH:mm') : '',
            dataIndex: 'gmtPayment'
        }, {
            title: '交易退款时间',
            sorter: true,
            width: '100px',
            customRender: (text) => text ? moment(text).format('YYYY-MM-DD HH:mm') : '',
            dataIndex: 'gmtRefund'
        }, {
            title: '交易结束时间',
            sorter: true,
            width: '100px',
            customRender: (text) => text ? moment(text).format('YYYY-MM-DD HH:mm') : '',
            dataIndex: 'gmtClose'
        }, {
            title: '支付金额信息',
            sorter: true,
            width: '100px',
            dataIndex: 'fundBillList'
        }, {
            title: '回传参数',
            sorter: true,
            width: '100px',
            dataIndex: 'passbackParams'
        }, {
            title: '优惠券信息',
            sorter: true,
            width: '100px',
            dataIndex: 'voucherDetailList'
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
            this.columns = columns
            return {
                // create model
                visible: false,
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
                    return getLbPayOrderList(requestParameters)
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
                        console.log('values', values)
                        if (values.id > 0) {
                            // 修改 e.g.

                            saveLbPayOrder(values).then(res => {
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
                            saveLbPayOrder(values).then(res => {
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
                batchDelLbPayOrder(this.selectedRowKeys).then(res => {
                    this.confirmLoading = false
                    // 刷新表格
                    this.$refs.table.refresh()

                    this.$message.info('删除成功')
                })
            },
            handleDel (record) {
                if (record.id > 0) {
                    // 修改 e.g.
                    delLbPayOrder(record).then(res => {
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
            }
        }
    }
</script>
