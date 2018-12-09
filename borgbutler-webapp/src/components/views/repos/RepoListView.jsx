import React from 'react'
import './RepoListView.css';
import {CardDeck} from 'reactstrap';
import {PageHeader} from '../../general/BootstrapComponents';
import {getRestServiceUrl} from '../../../utilities/global';
import ErrorAlert from '../../general/ErrorAlert';
import TemplateCard from './TemplateCard';
import {IconRefresh} from "../../general/IconComponents";
import I18n from "../../general/translation/I18n";

class RepoListView extends React.Component {


    path = getRestServiceUrl('repos');
    state = {
        isFetching: false
    };

    componentDidMount = () => {
        this.fetchTemplates();
    };

    fetchTemplates = () => {
        this.setState({
            isFetching: true,
            failed: false,
            definitions: undefined,
            templates: undefined
        });
        fetch(`${this.path}/list`, {
            method: 'GET',
            headers: {
                'Accept': 'application/json'
            }
        })
            .then(response => response.json())
            .then(json => {
                const definitions = json.templateDefinitions.reduce((accumulator, current) => ({
                    ...accumulator,
                    [current.refId]: current
                }), {});

                const templates = json.templates.map(template => {
                    if (typeof template.templateDefinition === 'object') {
                        //console.log('refId: ' + template.templateDefinition.refId + ', templateDefinition: ' + JSON.stringify(template.templateDefinition))
                        definitions[template.templateDefinition.refId] = template.templateDefinition;
                        template.templateDefinition = template.templateDefinition.refId;
                    }

                    return {
                        id: template.id,
                        primaryKey: template.fileDescriptor.primaryKey,
                        filename: template.fileDescriptor.filename,
                        lastModified: template.fileDescriptor.lastModified,
                        templateDefinitionId: template.templateDefinitionId,
                        templateDefinition: template.templateDefinition
                    };
                });

                this.setState({
                    isFetching: false,
                    definitions, templates
                })
            })
            .catch(() => this.setState({isFetching: false, failed: true}));
    };

    render = () => {
        let content = undefined;

        if (this.state.isFetching) {

            content = <i>Loading...</i>;

        } else if (this.state.failed) {

            content = <ErrorAlert
                title={'Cannot load Templates'}
                description={'Something went wrong during contacting the rest api.'}
                action={{
                    handleClick: this.fetchTemplates,
                    title: 'Try again'
                }}
            />;

        } else if (this.state.templates) {

            content = <React.Fragment>
                <div
                    className={'btn btn-outline-primary refresh-button-right'}
                    onClick={this.fetchTemplates}
                >
                    <IconRefresh/>
                </div>
                <CardDeck>
                {this.state.templates.map(template => {
                    const definition = this.state.definitions[template.templateDefinition];

                    return <TemplateCard
                        key={template.primaryKey}
                        template={template}
                        definition={definition}
                    />;
                })}
                </CardDeck>
            </React.Fragment>;

        }

        return <React.Fragment>
            <PageHeader>
                <I18n name={'templates'}/>
            </PageHeader>
            {content}
        </React.Fragment>;
    };

    constructor(props) {
        super(props);

        this.fetchTemplates = this.fetchTemplates.bind(this);
    }
}

export default RepoListView;
