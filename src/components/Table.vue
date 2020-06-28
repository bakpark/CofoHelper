<template>
    <div>
        <table>
            <tr>
                <th v-for="(column, index) in columns" :key="index">{{column}}</th>
            </tr>
            <tr v-for="(row, index) in rows" :key="index">
                <td v-for="(column, index) in columns" :key="index">{{row[column]}}</td>
            </tr>
        </table>
    </div>
</template>
<script>
/* eslint-disable */
export default {
    props:{
        columns: Array, // ['name', 'A', 'B', ,,, 'F']
        rows: Array     // [{name: 'bakpark', A: Accepted, ...}, {...}, {...}]
    }
}
</script>
<style>
    
</style>