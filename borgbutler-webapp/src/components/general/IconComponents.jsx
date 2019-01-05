import React from 'react';
import {
    faCaretDown,
    faCaretUp,
    faCheck,
    faCircleNotch,
    faDownload,
    faExclamationTriangle,
    faInfoCircle,
    faPlus,
    faSortDown,
    faSortUp,
    faSync,
    faTrash,
    faTimes,
    faSkullCrossbones,
    faUpload
} from '@fortawesome/free-solid-svg-icons'
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

function IconAdd() {
    return (
        <FontAwesomeIcon icon={faPlus}/>
    );
}

function IconCancel() {
    return (
        <FontAwesomeIcon icon={faTimes}/>
    );
}

function IconCancelJob() {
    return (
        <FontAwesomeIcon icon={faTimes} color={'red'} size={'lg'}/>
    );
}

function IconCheck() {
    return (
        <FontAwesomeIcon icon={faCheck}/>
    );
}

function IconCollapseClose() {
    return (
        <FontAwesomeIcon icon={faCaretUp}/>
    );
}

function IconCollapseOpen() {
    return (
        <FontAwesomeIcon icon={faCaretDown}/>
    );
}

function IconDanger() {
    return (
        <FontAwesomeIcon icon={faSkullCrossbones}/>
    );
}

function IconDownload() {
    return (
        <FontAwesomeIcon icon={faDownload}/>
    );
}

function IconInfo() {
    return (
        <FontAwesomeIcon icon={faInfoCircle}/>
    );
}

function IconRefresh() {
    return (
        <FontAwesomeIcon icon={faSync}/>
    );
}

function IconRemove() {
    return (
        <FontAwesomeIcon icon={faTrash}/>
    );
}

function IconSpinner() {
    return (
        <FontAwesomeIcon icon={faCircleNotch} spin={true} size={'3x'} color={'#aaaaaa'} />
    );
}

function IconSortDown() {
    return (
        <FontAwesomeIcon icon={faSortDown}/>
    );
}

function IconSortUp() {
    return (
        <FontAwesomeIcon icon={faSortUp}/>
    );
}

function IconUpload() {
    return (
        <FontAwesomeIcon icon={faUpload}/>
    );
}

function IconWarning() {
    return (
        <FontAwesomeIcon icon={faExclamationTriangle}/>
    );
}

export {
    IconAdd,
    IconCancel,
    IconCancelJob,
    IconCheck,
    IconCollapseClose,
    IconCollapseOpen,
    IconDanger,
    IconDownload,
    IconInfo,
    IconRefresh,
    IconRemove,
    IconSortDown,
    IconSortUp,
    IconSpinner,
    IconUpload,
    IconWarning
};
