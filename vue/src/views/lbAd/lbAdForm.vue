<template>
  <a-modal
    :title="title"
    :width="640"
    :visible="visible"
    :confirmLoading="loading"
    @ok="() => { $emit('ok') }"
    @cancel="() => { $emit('cancel') }"
  >
    <a-spin :spinning="loading">
      <a-form :form="form" v-bind="formLayout">
        <!-- 检查是否有 id 并且大于0，大于0是修改。其他是新增，新增不显示主键ID -->
        <a-form-item v-show="model && model.id > 0" label="编号1">
          <a-input v-decorator="['id', { initialValue: 0 }]" disabled />
        </a-form-item>
        <a-form-item label="标题">
          <a-input v-decorator="['name', {rules: [{required: true, min: 1, message: '请输入标题！'}]}]" />
        </a-form-item>
        <a-form-item label="介绍">
          <a-input v-decorator="['introduction', {rules: [{required: true, min: 1, message: '请输入广告介绍！'}]}]" />
        </a-form-item>
        <a-form-item label="图片上传" help="启动广告：1125*2436，首页广告：1125*840">
          <a-upload
            name="file"
            :multiple="false"
            list-type="picture"
            :file-list="fileList"
            @change="handleChange"
            action="/api/tencent/upload"
          >
            <a-button> <a-icon type="upload" />上传图片</a-button>
          </a-upload>
          <a-input v-decorator="['imgUrl', {initialValue: ''}]" type="hidden" />
        </a-form-item>
        <a-form-item style="margin-top:20px" label="广告分类">
          <a-radio-group v-decorator="['adType', { initialValue: 2 }]" @change="handleAdTypeChange">
            <a-radio :value="1">外部链接</a-radio>
            <a-radio :value="2">作者推荐</a-radio>
            <a-radio :value="3">栏目推荐</a-radio>
            <a-radio :value="4">期刊(目录)推荐</a-radio>
            <a-radio :value="5">文章推荐</a-radio>
          </a-radio-group>
        </a-form-item>
        <a-form-item label="链接地址" v-if="AdTypeValue == 1">
          <a-input v-decorator="['linkUrl', {rules: [{required: true, min: 1, message: '请输入链接地址！'}]}]" />
        </a-form-item>
        <a-form-item label="推荐作者" v-if="AdTypeValue == 2">
          <a-select @change="handleAuthorChange" v-decorator="['lbAuthorId', {rules: [{required: true, message: '请选择推荐作者！'}]}]" placeholder="请选择作者">
            <a-select-option v-for="lbAuthor in this.lbAuthorList" :key="lbAuthor.id" :value="lbAuthor.id">
              {{ lbAuthor.name }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="推荐栏目" v-if="AdTypeValue == 3">
          <a-select v-decorator="['lbSubjectId', {rules: [{required: true, message: '请选择推荐栏目！'}]}]">
            <a-select-option v-for="lbSubject in this.lbSubjectList" :key="lbSubject.id" :value="lbSubject.id">
              {{ lbSubject.title }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="推荐期刊" v-if="AdTypeValue == 4">
          <a-select v-decorator="['lbRdPeriodicalId', {rules: [{required: true, message: '请选择推荐期刊！'}]}]">
            <a-select-option v-for="lbPeriodical in this.lbPeriodicalList" :key="lbPeriodical.id">
              {{ lbPeriodical.title }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="推荐文章" v-if="AdTypeValue == 5">
          <a-select v-decorator="['lbPostId', {rules: [{required: true, message: '请选择推荐文章！'}]}]">
            <a-select-option v-for="lbPost in this.lbPostList" :key="lbPost.id">
              {{ lbPost.title }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="广告位置">
          <a-radio-group v-decorator="['adLocation', { initialValue: 1 }]"  @change="handleAdLocationChange">
            <a-radio :value="1">首页广告</a-radio>
            <a-radio :value="2">启动广告</a-radio>
          </a-radio-group>
        </a-form-item>
        <a-form-item label="首页显示期刊" v-if="AdLocationValue == 1">
          <a-select v-decorator="['lbPeriodicalId', {rules: [{required: true, message: '请选择所属期刊！'}]}]">
            <a-select-option v-for="lbPeriodical in this.lbPeriodicalList" :key="lbPeriodical.id">
              {{ lbPeriodical.title }}
            </a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="首页显示期刊下位置" v-if="AdLocationValue == 1">
          <a-input-number v-decorator="['lbPeriodicalIndex', {initialValue: 1,rules: [{required: true}]}]" />
        </a-form-item>
        <a-form-item label="状态">
          <a-radio-group v-decorator="['status', { initialValue: 100 }]">
            <a-radio :value="100">正常</a-radio>
            <a-radio :value="101">禁用</a-radio>
          </a-radio-group>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>
    import pick from 'lodash.pick'

    // 表单字段
    const fields = [
        'id',
        'name',
        'introduction',
        'imgUrl',
        'lbPeriodicalId',
        'lbRdPeriodicalId',
        'lbSubjectId',
        'lbPostId',
        'adLocation',
        'lbAuthorId',
        'lbPeriodicalIndex',
        'adType',
        'linkUrl',
        'status',
        'updateTime',
        'createTime'
    ]

    export default {
        props: {
            visible: {
                type: Boolean,
                required: true
            },
            title: {
                type: String,
                required: true
            },
            loading: {
                type: Boolean,
                default: () => false
            },
            model: {
                type: Object,
                default: () => null
            },
            lbAuthorList: {
              type: Array,
              default: () => null
            },
            lbSubjectList: {
              type: Array,
              default: () => null
            },
            lbPostList: {
              type: Array,
              default: () => null
            },
            lbPeriodicalList: {
                type: Array,
                default: () => null
            }
        },
        data () {
            this.formLayout = {
                labelCol: {
                    xs: { span: 24 },
                    sm: { span: 7 }
                },
                wrapperCol: {
                    xs: { span: 24 },
                    sm: { span: 13 }
                }
            }
            return {
                form: this.$form.createForm(this),
                AdTypeValue: 2,
                AdLocationValue: 1,
                dateFormat: 'YYYY-MM-DD HH:mm:ss',
                fileList: []
            }
        },
        created () {
            console.log('custom modal created')

            // 防止表单未注册
            fields.forEach(v => this.form.getFieldDecorator(v))

            // 当 model 发生改变时，为表单设置值
            this.$watch('model', () => {
                this.model && this.form.setFieldsValue(pick(this.model, fields))

                // 初始化图片上传
                if (this.model) {
                  this.fileList = [{
                                    uid: '-1',
                                    name: this.model.imgUrl,
                                    status: 'done',
                                    url: this.model.imgUrl
                                  }]
                  this.AdTypeValue = this.model.adType
                  this.AdLocationValue = this.model.adLocation
                } else {
                  this.fileList = []
                  this.AdTypeValue = 2
                  this.AdLocationValue = 1
                }
            })
        },
        methods: {
          handleAdTypeChange (e) {
               this.AdTypeValue = e.target.value
          },
          handleAdLocationChange (e) {
               this.AdLocationValue = e.target.value
          },
          handleAuthorChange (info) {
          },
          handleChange (info) {
            const fileListt = [...info.fileList]

            this.fileList = fileListt.slice(-1)

            if (info.file.status === 'uploading') {
              this.$emit('update:loading', true)
              return
            }
            if (info.file.status === 'done') {
              // Get this url from response in real world.
              this.$emit('update:loading', false)
              this.form.setFieldsValue({ imgUrl: info.file.response.result.url })
            }
          }
        }
    }
</script>
