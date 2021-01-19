<template>
  <a-modal
    :title="title"
    :width="840"
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
        <a-form-item label="角色名">
          <a-input v-decorator="['roleName', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" />
        </a-form-item>
        <a-form-item label="角色代码">
          <a-input v-decorator="['roleCode', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" />
        </a-form-item>
        <a-form-item label="备注">
          <a-input v-decorator="['memo', {rules: [{required: true, min: 1, message: '请输入至少五个字符的规则描述！'}]}]" />
        </a-form-item>
        <a-form-item label="包含用户">
          <a-transfer
            :data-source="mockData"
            :titles="['未包含', '已包含']"
            :target-keys="targetKeys"
            :selected-keys="selectedKeys"
            :render="item => item.title"
            :disabled="disabled"
            @change="handleChange"
            @selectChange="handleSelectChange"
          />
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
        'roleName',
        'roleCode',
        'memo',
        'status'
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
            sysUserList: {
                type: Array,
                default: () => null
            }
        },
        data () {
          const mockData = []
          for (let i = 0; i < this.sysUserList.length; i++) {
            console.log(this.sysUserList[i].id)
            var tempJson = {
              key: this.sysUserList[i].id.toString(),
              title: this.sysUserList[i].username,
              description: this.sysUserList[i].memo,
              disabled: false
            }
            mockData.push(tempJson)
          }

    // const oriTargetKeys = mockData.filter(item => +item.key % 3 > 1).map(item => item.key)
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
                mockData,
                targetKeys: [],
                selectedKeys: [],
                disabled: false
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
        },
        methods: {
          handleChange (nextTargetKeys, direction, moveKeys) {
            this.targetKeys = nextTargetKeys

            console.log('targetKeys: ', nextTargetKeys)
            console.log('direction: ', direction)
            console.log('moveKeys: ', moveKeys)
          },
          handleSelectChange (sourceSelectedKeys, targetSelectedKeys) {
            this.selectedKeys = [...sourceSelectedKeys, ...targetSelectedKeys]

            console.log('sourceSelectedKeys: ', sourceSelectedKeys)
            console.log('targetSelectedKeys: ', targetSelectedKeys)
          }
        }
    }
</script>
