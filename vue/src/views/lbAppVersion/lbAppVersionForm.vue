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
        <a-form-item v-show="model && model.id > 0" label="编号">
          <a-input v-decorator="['id', { initialValue: 0 }]" disabled />
        </a-form-item>
        <a-form-item label="是否强制更新">
          <a-radio-group>
            <a-radio :value="1">是</a-radio>
            <a-radio :value="2">否</a-radio>
          </a-radio-group>
        </a-form-item>
        <a-form-item label="上传APP安装包">
          <a-upload
            name="file"
            :multiple="true"
          >
            <a-button> <a-icon type="upload" />点击选择</a-button>
          </a-upload>
        </a-form-item>
        <a-form-item label="更新说明">
          <a-input v-decorator="['info', {rules: [{required: true, min: 1, message: '请输入更新说明！'}]}]" />
        </a-form-item>
        <a-form-item label="app类型">
          <a-select v-decorator="['appType', {rules: [{required: true,  message: '请选择APP类型！'}]}]">
                <a-select-option value="Android">Android</a-select-option>
                <a-select-option value="IOS">IOS</a-select-option>
              </a-select>
        </a-form-item>
        <a-form-item label="内部版本号">
          <a-input v-decorator="['innerVersion', {rules: [{required: true, min: 1, message: '请输入内部版本号！'}]}]" />
        </a-form-item>
        <a-form-item label="外部版本号">
          <a-input v-decorator="['externalVersion', {rules: [{required: true, min: 1, message: '请输入外部版本号！'}]}]" />
        </a-form-item>
        <a-form-item label="状态">
          <a-radio-group v-decorator="['状态', { initialValue: 100 }]">
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
        'isForce',
        'linkUrl',
        'info',
        'appType',
        'innerVersion',
        'externalVersion',
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
                form: this.$form.createForm(this)
            }
        },
        created () {
            console.log('custom modal created')

            // 防止表单未注册
            fields.forEach(v => this.form.getFieldDecorator(v))

            // 当 model 发生改变时，为表单设置值
            this.$watch('model', () => {
                this.model && this.form.setFieldsValue(pick(this.model, fields))
            })
        }
    }
</script>
