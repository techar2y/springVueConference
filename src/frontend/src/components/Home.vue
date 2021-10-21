<template>
    <div>
        <b-card
                overlay
                img-src="https://picsum.photos/900/250/?image=3"
                img-alt="Card Image"
                text-variant="white"
        >
        </b-card>
        <b-jumbotron header="Расписание конференций"
                     lead="Пользователь имеет возможность регистрироваться
                            на конференции и просматривать доклады.
                            Докладчики могут создавать доклады и выбирать время в аудитории для доклада.">
            <b-button to="/schedules" variant="primary">Перейти к расписанию</b-button>
        </b-jumbotron>
    </div>
</template>

<script>
    export default {
        name: "Home"
    }
</script>

<style scoped>

</style>