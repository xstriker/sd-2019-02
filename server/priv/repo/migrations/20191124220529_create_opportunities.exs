defmodule Server.Repo.Migrations.CreateOpportunities do
  use Ecto.Migration

  def change do
    create table(:opportunities) do
      add :nomeEmpresa, :string
      add :contato, :string
      add :area, :string
      add :tempo, :integer
      add :salario, :integer

      timestamps()
    end

  end
end
