<template>
  <page-header-wrapper>
    <a-card :bordered="false">
      <div class="table-page-search-wrapper">
        <a-form layout="inline">
          <a-row :gutter="48">
            <a-col :md="8" :sm="24">
              <a-form-item label="名称">
                <a-input v-model="queryParam.title" placeholder=""/>
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

      <div class="table-operator">
        <a-button type="primary" icon="plus" @click="handleAdd">新建</a-button>
        <a-dropdown v-action:edit v-if="selectedRowKeys.length > 0">
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
        :scroll="{ x: 1900}"
        showPagination="auto"
      >
        <span slot="serial" slot-scope="text, record, index">
          {{ index + 1 }}
        </span>
        <span slot="status" slot-scope="text">
          <a-badge :status="text | statusTypeFilter" :text="text | statusFilter" />
        </span>
        <span slot="linkslot" slot-scope="text">
          <a :href="text" target="_blank">访问</a>
        </span>
        <span slot="authorslot" slot-scope="text">
          {{ text | authorFilter }}
        </span>
        <span slot="imgslot" slot-scope="text">
          <img alt="example" style="width: 50px;height:50px" @click="handlePreview(text)" :src="text" />
        </span>
        <span slot="action" slot-scope="text, record">
          <template>
            <a @click="handleEdit(record)">修改</a>
            <a-divider type="vertical" />
            <a-popconfirm title="是否要删除当前数据？" @confirm="handleDel(record)">
              <a style="color: red">删除</a>
            </a-popconfirm>
          </template>
        </span>
      </s-table>

      <a-modal :visible="previewVisible" :footer="null" @cancel="handlePreviewCancel">
        <img alt="example" style="width: 100%" :src="previewImage" />
      </a-modal>

      <edit-form
        ref="editForm"
        :title="title"
        :visible="visible"
        :loading.sync="confirmLoading"
        :model="mdl"
        :lbCatalogList="lbCatalogList"
        :lbAuthorList="lbAuthorList"
        :lbSubjectList="lbSubjectList"
        :lbPeriodicalList="lbPeriodicalList"
        @cancel="handleCancel"
        @ok="handleOk"
      />
    </a-card>
  </page-header-wrapper>
</template>

<script>
    import moment from 'moment'
    import { STable, Ellipsis } from '@/components'
    import { statusMap, recommendMap } from '@/api/RC'
    import { getLbPostList, saveLbPost, delLbPost, batchDelLbPost } from '@/api/lbPost'
    import { getLbCatalogListAll } from '@/api/lbCatalog'
    import { getLbSubjectListAll } from '@/api/lbSubject'
    import { getLbAuthorListAll } from '@/api/lbAuthor'
    import { getLbPeriodicalListAll } from '@/api/lbPeriodical'
    import EditForm from './lbPostForm'

    const columns = [
        // {
        //     title: 'id',
        //     sorter: true,
        //     fixed: 'left',
        //     width: '80px',
        //     dataIndex: 'id'
        // },
        {
            title: '标题',
            sorter: true,
            fixed: 'left',
            width: '150px',
            dataIndex: 'title'
        }, {
            title: '期刊',
            sorter: true,
            width: '150px',
            dataIndex: 'periodicalTitle'
        }, {
            title: '栏目',
            sorter: true,
            width: '100px',
            dataIndex: 'postSubject'
        }, {
            title: '分类',
            sorter: true,
            width: '100px',
            dataIndex: 'postCatalog'
        }, {
            title: '显示样式',
            sorter: true,
            width: '100px',
            customRender: (value) => showTypeMap[value].text,
            dataIndex: 'showType'
        }, {
            title: '图片',
            sorter: true,
            width: '100px',
            scopedSlots: { customRender: 'imgslot' },
            dataIndex: 'imgUrl'
        }, {
            title: '文章图片',
            sorter: true,
            width: '100px',
            scopedSlots: { customRender: 'imgslot' },
            dataIndex: 'preimgUrl'
        }, {
            title: '广告链接地址',
            sorter: true,
            width: '100px',
            scopedSlots: { customRender: 'linkslot' },
            dataIndex: 'linkUrl'
        }, {
            title: '作者',
            sorter: true,
            width: '100px',
            scopedSlots: { customRender: 'authorslot' },
            dataIndex: 'author'
        }, {
            title: '排序',
            sorter: true,
            width: '100px',
            dataIndex: 'postOrder'
        }, {
            title: '发布时间',
            sorter: true,
            width: '150px',
            customRender: (text) => text ? moment(text).format('YYYY-MM-DD HH:mm') : '',
            dataIndex: 'publishedAt'
        },
        {
            title: '推荐',
            sorter: true,
            customRender: (value) => recommendMap[value].text,
            width: '100px',
            dataIndex: 'recommend'
        }, {
            title: '状态',
            sorter: true,
            width: '100px',
            scopedSlots: { customRender: 'status' },
            dataIndex: 'status'
        }, {
            title: '修改时间',
            sorter: true,
            width: '150px',
            customRender: (text) => text ? moment(text).format('YYYY-MM-DD') : '',
            dataIndex: 'updateTime'
        }, {
            title: '创建时间',
            sorter: true,
            width: '150px',
            customRender: (text) => text ? moment(text).format('YYYY-MM-DD') : '',
            dataIndex: 'createTime'
        },
        {
            title: '操作',
            dataIndex: 'action',
            width: '150px',
            fixed: 'right',
            scopedSlots: { customRender: 'action' }
        }
    ]

    const showTypeMap = {
    1: {
        status: 'default',
        text: '左右图文'
    },
    2: {
        status: 'processing',
        text: '上下图文'
    },
    4: {
        status: 'processing',
        text: '无图'
    }
  }

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
                previewVisible: false,
                previewImage: '',
                lbCatalogList: [],
                lbAuthorList: [],
                lbSubjectList: [],
                lbPeriodicalList: [],
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
                    return getLbPostList(requestParameters)
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
            },
            authorFilter (type) {
                var retlist = ''
                var jsonlist = JSON.parse(type)
                for (var i in jsonlist) {
                    retlist = retlist + '  ' + jsonlist[i].name
                }
                return retlist
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
        created () {
            // 初始化数据
            getLbCatalogListAll()
                        .then(res => {
                            this.lbCatalogList = res.result
                        })
            getLbSubjectListAll()
                        .then(res => {
                            this.lbSubjectList = res.result
                        })
            getLbAuthorListAll()
                        .then(res => {
                            this.lbAuthorList = res.result
                        })
            getLbPeriodicalListAll()
                        .then(res => {
                            this.lbPeriodicalList = res.result
                        })
        },
        methods: {
            getAuthor (jsonstr) {
                var author = ''
                for (var i in jsonstr) {
                    author = author + jsonstr[i].name
                    }
            },
            async handlePreview (url) {
                this.previewImage = url
                this.previewVisible = true
            },
            handlePreviewCancel () {
                this.previewVisible = false
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
                            console.log(values)
                            // 日期格式化
                            values.publishedAt = moment(values.publishedAt).format('YYYY-MM-DD HH:mm:ss')

                        if (values.id > 0) {
                            // 修改 e.g.

                            saveLbPost(values).then(res => {
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
                            saveLbPost(values).then(res => {
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
                batchDelLbPost(this.selectedRowKeys).then(res => {
                    this.confirmLoading = false
                    // 刷新表格
                    this.$refs.table.refresh()

                    this.$message.info('删除成功')
                })
            },
            handleDel (record) {
                if (record.id > 0) {
                    // 修改 e.g.
                    delLbPost(record).then(res => {
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
<style>
</style>
