
<template>
    <div>
        <Table v-for="(contestInfo, index) in contestsInfo"
            :key="index"
            :columns="contestInfo.columns"
            :rows="contestInfo.rows"></Table>
    </div>
</template>
<script>
/* eslint-disable */
import Table from '@/components/Table.vue';
import axios from 'axios';

export default {
    data() {
        return {
            submissions: [],
        }
    },
    computed: {
        contestsInfo(){
            const members = this.$store.state.members;
            const contests = this.$store.state.contests;

            let submissionsFiltered = this.submissions
                .filter(submission => contests.includes(submission.contestId));
            
            // columns: ['name' 'A', 'B', 'C', ... 'F']
            // rows: [
            //          {name: bakpark, A:Accepted, B:Accepted, ,,, F:Accepted},
            //          {name: sw, A:Accepted, B:Accepted, ,,, F:Accepted},
            //          {name: kibaek, A:Accepted, B:Accepted, ,,, F:Accepted},
            //       ];

            // contest 별로, member들의 성적 기록
            let contestsInfo = [];
            contests.forEach(contest=>{
                let tempRows = {};
                members.forEach(member => {
                    tempRows[member] = {
                        name: member
                    }
                });

                submissionsFiltered.forEach(submission=>{
                    let name = submission.author.members[0].handle; // bakpark
                    let problem = submission.problem.index; //  C
                    let solved = submission.verdict;    // OK

                    // 결과가 OK -> 덮어쓰고 끝
                    if(solved == 'OK'){
                        tempRows[name][problem] = 'OK';
                    }
                    // 결과가 FAIL -> 기존Fail 횟수 증가
                    else if(tempRows[name][problem] != 'OK'){
                        let failCount = tempRows[name][problem];
                        tempRows[name][problem] = failCount == undefined ? 1 : failCount + 1;
                    } 
                });

                let contestInfo = {
                    columns: ['name', 'A', 'B', 'C', 'D', 'E', 'F'],
                    rows: Object.values(tempRows)
                };
                contestsInfo.push(contestInfo);
            });
            return contestsInfo;
        }
    },
    components:{
        Table
    },
    created() {
        const members = this.$store.state.members;
        const contests = this.$store.state.contests;
        console.log(members);
        // contests => contest 하나당 테이블 하나 생성
        members.forEach(member => {
            this.$axios.get(
                '/user.status',
                {
                    params: {
                        handle: member,
                        from: 1,
                        count: 50
                    }
                }
            ).then(res => {
                this.submissions = this.submissions.concat(res.data.result);
            });
        });
    },
}
</script>
<style>
    
</style>