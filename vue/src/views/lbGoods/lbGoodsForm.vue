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
        <a-form-item label="商品编号">
          <a-input v-decorator="['tcode', {rules: [{required: true, min: 1, message: '请输入至少五个字符！'}]}]" />
        </a-form-item>
        <a-form-item label="类型">
          <a-radio-group v-decorator="['devType', { initialValue: 1 }]">
            <a-radio :value="1">包年</a-radio>
            <a-radio :value="2">单点</a-radio>
          </a-radio-group>
        </a-form-item>
        <a-form-item label="类型">
          <a-radio-group v-decorator="['ttype', { initialValue: 1 }]">
            <a-radio :value="1">Android</a-radio>
            <a-radio :value="2">IOS</a-radio>
          </a-radio-group>
        </a-form-item>
        <a-form-item label="标题">
          <a-input v-decorator="['title', {rules: [{required: true, min: 1, message: '请输入至少五个字符！'}]}]" />
        </a-form-item>
        <a-form-item label="描述">
          <a-input v-decorator="['body', {rules: [{required: false}]}]" />
        </a-form-item>
        <a-form-item label="详细展现">
          <a-input v-decorator="['info', {rules: [{required: false}]}]" />
        </a-form-item>
        <a-form-item label="价格">
          <a-input-number
            style="width: 100%"
            :formatter="value => `￥ ${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')"
            :parser="value => value.replace(/\￥\s?|(,*)/g, '')"
            addon-after="元"
            v-decorator="['price', {rules: [{required: true, message: '请输入商品价格！'}]}]" />
        </a-form-item>
        <a-form-item label="折扣">
          <a-input-number
            style="width: 100%"
            :formatter="value => `￥ ${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')"
            :parser="value => value.replace(/\￥\s?|(,*)/g, '')"
            v-decorator="['discount', {rules: [{required: false}]}]" />
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
        'tcode',
        'ttype',
        'devType',
        'title',
        'body',
        'info',
        'price',
        'discount',
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
