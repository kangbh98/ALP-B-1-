<script setup>
defineProps({
    image: {
        type: String,
        required: true,
    },
    title: {
        type: String,
        required: true,
    },
    description: {
        type: String,
        required: true,
    },
    action: {
        type: Object,
        color: String,
        route: String,
        label: String,
        default: () => ({
            color: "bg-gradient-success",
            label: "Find Out More",
        }),
    },
});
</script>
<template>
    <div class="card">
        <div class="card-body text-center">
            <img :src="image" :alt="title" class="img-fluid border-radius-lg">
            <h5 class="font-weight-normal">
                <a href="javascript:;">{{ title }}</a>
            </h5>
            <p class="mb-0">
                {{ description }}
            </p>
            <button type="button" class="btn btn-sm mb-0 mt-3" :class="action.color">
                {{ action.label }}
            </button>
        </div>
    </div>
</template>
