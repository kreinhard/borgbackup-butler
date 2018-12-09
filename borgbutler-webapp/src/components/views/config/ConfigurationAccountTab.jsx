import React from 'react';
import {UncontrolledTooltip} from 'reactstrap';
import {
    FormLabelField,
    FormSelect, FormOption, FormGroup, FormLabel, FormField
} from "../../general/forms/FormComponents";
import {getRestServiceUrl} from "../../../utilities/global";
import {clearDictionary} from '../../../utilities/i18n';
import I18n from "../../general/translation/I18n";
import ErrorAlertGenericRestFailure from "../../general/ErrorAlertGenericRestFailure";
import Loading from "../../general/Loading";
import {IconRefresh} from "../../general/IconComponents";

class ConfigAccountTab extends React.Component {
    loadConfig = () => {
        this.setState({
            loading: true,
            failed: false
        });
        fetch(getRestServiceUrl('configuration/user'), {
            method: 'GET',
            dataType: 'JSON',
            headers: {
                'Content-Type': 'text/plain; charset=utf-8'
            }
        })
            .then((resp) => {
                return resp.json()
            })
            .then((data) => {
                const {locale, dateFormat, ...user} = data;
                this.setState({
                    loading: false,
                    locale: locale ? locale : '',
                    dateFormat: dateFormat ? dateFormat : '',
                    ...user
                })
            })
            .catch((error) => {
                console.log("error", error);
                this.setState({
                    loading: false,
                    failed: true
                });
            })
    };

    constructor(props) {
        super(props);

        this.state = {
            loading: true,
            failed: false,
            locale: null,
            dateFormat: null
        };

        this.handleTextChange = this.handleTextChange.bind(this);
        this.loadConfig = this.loadConfig.bind(this);
    }

    componentDidMount() {
        this.loadConfig();
    }

    handleTextChange = event => {
        event.preventDefault();
        this.setState({[event.target.name]: event.target.value});
    }

    save() {
        var user = {
            locale: this.state.locale,
            dateFormat: this.state.dateFormat
        };
        return fetch(getRestServiceUrl("configuration/user"), {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(user)
        })
    }

    render() {
        if (this.state.loading) {
            return <Loading/>;
        }

        if (this.state.failed) {
            return <ErrorAlertGenericRestFailure handleClick={this.loadConfig}/>;
        }

        return (
            <form>
                <div id={'clearDictionary'}
                     className={'btn btn-outline-primary refresh-button-right'}
                     onClick={clearDictionary}
                >
                    <IconRefresh/>
                    <UncontrolledTooltip placement={'left'} target={'clearDictionary'}>
                        <I18n name={'configuration.reloadDictionary.hint'}/>
                    </UncontrolledTooltip>
                </div>
                <FormLabelField label={<I18n name={'configuration.application.language'}/>} fieldLength={2}>
                    <FormSelect value={this.state.locale} name={'locale'} onChange={this.handleTextChange}>
                        <FormOption value={''} i18nKey={'configuration.application.language.option.default'}/>
                        <FormOption value={'en'} i18nKey={'language.english'}/>
                        <FormOption value={'de'} i18nKey={'language.german'}/>
                    </FormSelect>
                </FormLabelField>
                <FormGroup>
                    <FormLabel length={2} htmlFor={'dateFormat'}>
                        <I18n name={'configuration.application.dateFormat'}/>
                    </FormLabel>
                    <FormField length={2}>
                        <FormSelect value={this.state.dateFormat} name={'dateFormat'} onChange={this.handleTextChange}>
                            <FormOption value={''} i18nKey={'configuration.application.dateFormat.option.auto'}/>
                            <FormOption value={'dd.MM.yyyy'} label={'16.01.2018'}/>
                            <FormOption value={'d.M.yy'} label={'16.1.18'}/>
                            <FormOption value={'yyyy-MM-dd'} label={'2018-01-16'}/>
                            <FormOption value={'dd/MM/yyyy'} label={'16/01/2018'}/>
                            <FormOption value={'d/M/yy'} label={'16/1/18'}/>
                            <FormOption value={'MM/dd/yyyy'} label={'01/16/2018'}/>
                            <FormOption value={'M/d/yy'} label={'1/16/18'}/>
                        </FormSelect>
                    </FormField>
                </FormGroup>
            </form>
        );
    }
}

export default ConfigAccountTab;

