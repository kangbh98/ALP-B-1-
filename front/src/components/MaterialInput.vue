<script setup>
import { defineEmits, defineProps } from "vue";

const props = defineProps({
    id: {
        type: String,
        default: "",
    },
    type: {
        type: String,
        default: "text",
    },
    label: {
        type: [String, Object],
        text: String,
        class: String,
        default: () => ({
            class: "",
        }),
    },
    modelValue: {
        type: String,
        default: "",
    },
    placeholder: {
        type: String,
        default: "",
    },
    size: {
        type: String,
        default: "md",
    },
    error: {
        type: Boolean,
        default: false,
    },
    success: {
        type: Boolean,
        default: false,
    },
    isRequired: {
        type: Boolean,
        default: false,
    },
    isDisabled: {
        type: Boolean,
        default: false,
    },
    inputClass: {
        type: String,
        default: "",
    },
    icon: {
        type: String,
        default: "",
    },
});

const emit = defineEmits(["update:modelValue"]);

function getClasses(size, success, error) {
    let sizeValue, isValidValue;

    sizeValue = size && `form-control-${size}`;

    if (error) {
        isValidValue = "is-invalid";
    } else if (success) {
        isValidValue = "is-valid";
    } else {
        isValidValue = "";
    }

    return `${sizeValue} ${isValidValue}`;
}

const updateValue = (event) => {
    emit("update:modelValue", event.target.value);
};
</script>

<template>
    <div class="input-group">
        <label v-if="label" :class="[typeof label === 'object' && label.class ? label.class : '', 'label-style']">
            {{ typeof label === "object" ? label.text : label }}
        </label>
        <span v-if="icon" class="input-group-text"><i :class="`fa-${icon}`" aria-hidden="true"></i></span>
        <input
            :id="id"
            :type="type"
            class="form-control"
            :class="[getClasses(size, success, error), inputClass]"
            :value="modelValue"
            @input="updateValue"
            :placeholder="placeholder"
            :required="isRequired"
            :disabled="isDisabled"
        />
    </div>
</template>
<style>
.label-style {
    font-size: 24px;
    /* h3와 같은 글씨 크기 */
    color: #343a40;
    /* 어두운 글씨 색상 */
    margin-top: 8px;
    /* 상단 여백 조정 */
    font-weight: bold;
    /* 글씨 두께 */
}
</style>
