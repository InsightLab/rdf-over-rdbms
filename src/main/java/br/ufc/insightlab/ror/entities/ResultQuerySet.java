package br.ufc.insightlab.ror.entities;

import it.unibz.krdb.obda.owlrefplatform.owlapi3.QuestOWLConnection;
import it.unibz.krdb.obda.owlrefplatform.owlapi3.QuestOWLResultSet;
import it.unibz.krdb.obda.owlrefplatform.owlapi3.QuestOWLStatement;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLObject;

import java.util.Iterator;

public class ResultQuerySet implements Iterable<ResultQuery> {

    private QuestOWLConnection connection;
    private String sparqlQuery;

    public ResultQuerySet(QuestOWLConnection conn, String query) {
        connection = conn;
        sparqlQuery = query;
    }

    @Override
    public Iterator<ResultQuery> iterator() {
        return new ResultIterator(connection, sparqlQuery);
    }


    private class ResultIterator implements Iterator<ResultQuery> {

        QuestOWLResultSet resultSet;
        QuestOWLStatement statement;
        QuestOWLConnection connection;
        int id;
        private boolean next;

        ResultIterator(QuestOWLConnection conn, String query) {
            connection = conn;
            try {
                statement = connection.createStatement();
                resultSet = statement.executeTuple(query);
            } catch (OWLException e) {
                e.printStackTrace();
                closeConenction();
            }

            id = 0;

            if (resultSet == null)
                next = false;
            else
                try {
                    next = resultSet.nextRow();
                }
                catch (OWLException e) {
                    next = false;
                }

        }

        private void closeConenction(){
            /*
             * Close connection and resources
             */
            try {
                if (statement != null && !statement.isClosed()) {
                    statement.close();
                }
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (OWLException e){
                e.printStackTrace();
            }
        }

        @Override
        public boolean hasNext() {
            return next;
        }

        @Override
        public ResultQuery next() {
            if(hasNext()) {
                ResultQuery entidade = new ResultQuery(id);

                try {
                    int columnSize = resultSet.getColumnCount();
                    id++;
                    for (int idx = 1; idx <= columnSize; idx++) {
                        OWLObject binding = resultSet.getOWLObject(idx);

                        entidade.addValue(resultSet.getSignature().get(idx - 1), binding.toString());
                    }
                } catch (OWLException e) {
                    e.printStackTrace();
                }

                try {
                    next = resultSet.nextRow();
                }
                catch (OWLException e) {
                    next = false;
                }

                return entidade;
            }
            else throw new Error("next on empty iterator");
        }

        @Override
        public void remove() {
            throw new RuntimeException("Remove method is not implemented!");
        }
    }
}
