class ValidatePresentationFormUtil {
    validateTitle(context) {
        if (context.currentPresentation.title.length < 1) {
            context.validationTitleInfo.invalid = "Поле обязательное к заполнению";
            context.validationTitleInfo.value = false;
            return context.validationTitleInfo.value;
        } else if (context.currentPresentation.title.length > 31) {
            context.validationTitleInfo.invalid = "Заголовок не может быть больше 32 символов";
            context.validationTitleInfo.value = false;
            return context.validationTitleInfo.value;
        } else {
            context.validationTitleInfo.value = true;
            return context.validationTitleInfo.value;
        }
    }

    validateRoom(context) {
        if (context.selectedRoomId == null) {
            context.validationRoomInfo.value = false;
            return context.validationRoomInfo.value;
        }

        let room = context.rooms.find(x => x.value === context.selectedRoomId);
        context.currentPresentation.room.id = room.value;
        context.currentPresentation.room.number = room.text.substring(room.text.indexOf(" ") + 1);
        context.validationRoomInfo.value = true;
        return context.validationRoomInfo.value;
    }

    validateLasts(context) {
        if (context.currentPresentation.lasts.length === 0) {
            context.validationLastsInfo.invalid = "Введите продолжительность доклада";
            context.validationLastsInfo.value = false;
            return context.validationLastsInfo.value;
        }
        context.validationLastsInfo.value = true;
        return context.validationLastsInfo.value;
    }

    validateStartTime(context) {
        if (context.currentPresentation.startTime.length === 0) {
            context.validationStartTimeInfo.invalid = "Введите время начало доклада";
            context.validationStartTimeInfo.value = false;
            return context.validationStartTimeInfo.value;
        }
        context.validationStartTimeInfo.value = true;
        return context.validationStartTimeInfo.value;
    }


    validateSubject(context) {
        if (context.currentPresentation.subject.length < 1) {
            context.validationSubjectInfo.invalid = "Пожалуйста введите название предмета";
            context.validationSubjectInfo.value = false;
            return context.validationSubjectInfo.value;
        }

        context.validationSubjectInfo.value = true;
        return context.validationSubjectInfo.value;
    }

    validatePresenter(context) {
        if (context.selectedPresenterId == null) {
            context.validationPresenterInfo.value = false;
            return context.validationPresenterInfo.value;
        }

        let presenter = context.presenters.find(x => x.id === context.selectedPresenterId);
        context.currentPresentation.presenters = [];
        context.currentPresentation.presenters.push(presenter);
        context.validationPresenterInfo.value = true;
        return context.validationPresenterInfo.value;
    }

    validateDate(context) {
        if (context.currentPresentation.date.length === 0) {
            context.validationDateInfo.invalid = "Нужно выбрать дату доклада";
            context.validationDateInfo.value = false;
            return context.validationDateInfo.value;
        }
        context.validationDateInfo.value = true;
        return context.validationDateInfo.value;
    }
}

export default new ValidatePresentationFormUtil()